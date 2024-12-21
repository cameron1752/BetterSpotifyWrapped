package Engine;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class AnalyzerEngine {
    public List<SpotifyData> data = new ArrayList<>();
    public int number_of_songs = 0;
    public String beforeDate;
    public String afterDate;
    public int N = 5;
    public List<String> topSongs = new ArrayList<>();
    public List<String> topArtists = new ArrayList<>();

    public void init(String date, String afterDate) {
        this.beforeDate = date;
        this.afterDate = afterDate;

        topSongs.clear();
        topArtists.clear();

        List<String> files = new ArrayList<>();

        files.add("/Users/cam/IdeaProjects/BetterSpotifyWrapped/src/main/resources/Spotify Extended Streaming History/Streaming_History_Audio_2014-2018_0.json");
        files.add("/Users/cam/IdeaProjects/BetterSpotifyWrapped/src/main/resources/Spotify Extended Streaming History/Streaming_History_Audio_2018-2019_1.json");
        files.add("/Users/cam/IdeaProjects/BetterSpotifyWrapped/src/main/resources/Spotify Extended Streaming History/Streaming_History_Audio_2019-2022_2.json");
        files.add("/Users/cam/IdeaProjects/BetterSpotifyWrapped/src/main/resources/Spotify Extended Streaming History/Streaming_History_Audio_2022-2023_3.json");
        files.add("/Users/cam/IdeaProjects/BetterSpotifyWrapped/src/main/resources/Spotify Extended Streaming History/Streaming_History_Audio_2023-2024_4.json");

        data = SpotifyData.readJsonFiles(files);
    }

    // prints list of top N songs by given year
    public void topSongsByYear(){
        Map<String, Integer> songCountMap = new HashMap<>();

        for (SpotifyData sd : data) {
            if (sd.isAfter(beforeDate) && sd.isBefore(afterDate)){
                String trackName = sd.getMasterMetadataTrackName();

                if (trackName != null && !trackName.isEmpty()) {
                    songCountMap.put(trackName, songCountMap.getOrDefault(trackName, 0) + 1);
                }
            }
        }

        System.out.println(songCountMap.size() + " unique songs between dates: " + beforeDate + " " + afterDate);
        number_of_songs = songCountMap.size();

        printMap(songCountMap, true);

    }

    // prints list of top N artists by given year
    public void topArtistsByYear(){
        Map<String, Integer> artistCountMap = new HashMap<>();

        for (SpotifyData sd : data) {
            if (sd.isAfter(beforeDate) && sd.isBefore(afterDate)){
                String artistName = sd.getMasterMetadataAlbumArtistName();

                if (artistName != null && !artistName.isEmpty()) {
                    artistCountMap.put(artistName, artistCountMap.getOrDefault(artistName, 0) + 1);
                }
            }
        }

        System.out.println(artistCountMap.size() + " artists after between dates: " + beforeDate + " " + afterDate);

        printMap(artistCountMap, false);
        topNArtists(artistCountMap);

    }

    // prints a hash map in a formatted manner
    public void printMap(Map<String, Integer> songCountMap, boolean isSong){
        songCountMap = sortByValue(songCountMap, true);
        ArrayList<Integer> songs = new ArrayList<>(songCountMap.values());
        ArrayList<String> songTitles = new ArrayList<>(songCountMap.keySet());

        for (int x = 0; x < N; x++){
            System.out.println((x+1) + ".  " + songTitles.get(x) + " :: " + songs.get(x));
            if (isSong){
                topSongs.add((x+1) + ".  " + songTitles.get(x) + "\n" + songs.get(x) + " plays");
            } else {
                topArtists.add((x+1) + ". " + songTitles.get(x) + "\n" + songs.get(x) + " plays");
            }

        }
        System.out.println();
    }

    // sorts map of artists / song titles by their count values either ascending or descending
    public Map<String, Integer> sortByValue(Map<String, Integer> map, boolean descending) {

        return map.entrySet()
                .stream()
                .sorted((e1, e2) -> descending
                        ? e2.getValue().compareTo(e1.getValue())
                        : e1.getValue().compareTo(e2.getValue())) // Comparator for sorting
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // Merge function
                        LinkedHashMap::new // Preserve insertion order
                ));
    }

    // method to call getTopNSongs for top N artists
    public void topNArtists(Map<String, Integer> artistCountMap){
        artistCountMap = sortByValue(artistCountMap, true);
        ArrayList<String> songs = new ArrayList<>(artistCountMap.keySet());
        for (int x = 0; x < N; x++) {
            getTopNSongs(songs.get(x));
        }
    }

    // Method to get the top N songs for a given artist
    public void getTopNSongs(String artistName) {
        // Step 1: Filter data for the given artist
        List<SpotifyData> artistData = data.stream()
                .filter(data -> artistName.equals(data.getMasterMetadataAlbumArtistName()))
                .collect(Collectors.toList());

        // Step 2: Aggregate msPlayed per track
        Map<String, Long> trackPlayTimeMap = new HashMap<>();
        for (SpotifyData data : artistData) {
            String trackName = data.getMasterMetadataTrackName();
            trackPlayTimeMap.put(trackName, trackPlayTimeMap.getOrDefault(trackName, 0L) + data.getMsPlayed());
        }

        // Step 3: Sort tracks by msPlayed in descending order
        List<Map.Entry<String, Long>> sortedTracks = trackPlayTimeMap.entrySet().stream()
                .sorted((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()))
                .collect(Collectors.toList());

        // Step 4: Get top N tracks
        List<String> topSongs = new ArrayList<>();
        for (int i = 0; i < Math.min(N, sortedTracks.size()); i++) {
            topSongs.add(sortedTracks.get(i).getKey());
        }

        System.out.println("Top " + N + " songs for artist " + artistName);
        for (int x = 0; x < N; x++) {
            System.out.println((x+1) + ".  " + topSongs.get(x));
        }
        System.out.println();
    }

    // Method to find the top N longest gaps between plays of a song
    public void findTopNLongestGaps() {
        // SimpleDateFormat to parse the timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // Group the data by song name, ensuring no null track names
        Map<String, List<SpotifyData>> songDataMap = data.stream()
                .filter(data -> data.getMasterMetadataTrackName() != null)  // Filter out null track names
                .collect(Collectors.groupingBy(SpotifyData::getMasterMetadataTrackName));

        // List to store the longest gap per song
        List<Map.Entry<String, Long>> longestGaps = new ArrayList<>();

        // Iterate over each song's data
        for (Map.Entry<String, List<SpotifyData>> entry : songDataMap.entrySet()) {
            String songName = entry.getKey();
            List<SpotifyData> songData = entry.getValue();

            // Sort the song data by timestamp
            songData.sort(Comparator.comparing(SpotifyData::getTs));

            // Initialize variables for tracking the longest gap
            long longestGap = 0;
            Date previousTimestamp = null;

            // Iterate through the sorted song data to calculate the gap
            for (SpotifyData song : songData) {
                try {
                    Date currentTimestamp = sdf.parse(song.getTs());

                    if (previousTimestamp != null) {
                        // Calculate the gap between current and previous play
                        long gap = currentTimestamp.getTime() - previousTimestamp.getTime();

                        // Update the longest gap if necessary
                        if (gap > longestGap) {
                            longestGap = gap;
                        }
                    }

                    // Update the previous timestamp
                    previousTimestamp = currentTimestamp;
                } catch (ParseException e) {
                }
            }

            // Store the longest gap for this song
            if (longestGap > 0) {
                longestGaps.add(new AbstractMap.SimpleEntry<>(songName, longestGap));
            }
        }

        // Sort by the longest gap in descending order
        longestGaps.sort((entry1, entry2) -> Long.compare(entry2.getValue(), entry1.getValue()));

        // Return the top N longest gaps
        for (int x = 0; x < N; x++){
            System.out.println((x+1) + ".  " + longestGaps.get(x).getKey());
        }
    }

    // getters and setters for date
    public void setBeforeDate(String beforeDate){
        this.beforeDate = beforeDate;
    }

    public String getBeforeDate(){
        return this.beforeDate;
    }

    // getters and setters for N
    public void setN(int N){ this.N = N; }

    public int getN(){ return this.N; }

}
