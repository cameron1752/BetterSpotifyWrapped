import java.util.*;
import java.util.stream.Collectors;

public class AnalyzerEngine {

    public static List<SpotifyData> data = new ArrayList<>();
    public static String date;
    public static int N = 5;

    public static void init(String date) {
        AnalyzerEngine.date = date;

        List<String> files = new ArrayList<>();

        files.add("/Users/cam/IdeaProjects/BetterSpotifyWrapped/src/main/resources/Spotify Extended Streaming History/Streaming_History_Audio_2014-2018_0.json");
        files.add("/Users/cam/IdeaProjects/BetterSpotifyWrapped/src/main/resources/Spotify Extended Streaming History/Streaming_History_Audio_2018-2019_1.json");
        files.add("/Users/cam/IdeaProjects/BetterSpotifyWrapped/src/main/resources/Spotify Extended Streaming History/Streaming_History_Audio_2019-2022_2.json");
        files.add("/Users/cam/IdeaProjects/BetterSpotifyWrapped/src/main/resources/Spotify Extended Streaming History/Streaming_History_Audio_2022-2023_3.json");
        files.add("/Users/cam/IdeaProjects/BetterSpotifyWrapped/src/main/resources/Spotify Extended Streaming History/Streaming_History_Audio_2023-2024_4.json");

        data = SpotifyData.readJsonFiles(files);
    }

    // prints list of top N songs by given year
    public static void topSongsByYear(){
        Map<String, Integer> songCountMap = new HashMap<>();
        int count = 0;

        for (SpotifyData sd : data) {
            if (sd.isAfter(date)){
                String trackName = sd.getMasterMetadataTrackName();

                if (trackName != null && !trackName.isEmpty()) {
                    songCountMap.put(trackName, songCountMap.getOrDefault(trackName, 0) + 1);
                }
                count++;
            }
        }

        System.out.println(songCountMap.size() + " unique songs after " + date);

        printMap(songCountMap);

    }

    // prints list of top N artists by given year
    public static void topArtistsByYear(){
        Map<String, Integer> artistCountMap = new HashMap<>();

        for (SpotifyData sd : data) {
            if (sd.isAfter(date)){
                String artistName = sd.getMasterMetadataAlbumArtistName();

                if (artistName != null && !artistName.isEmpty()) {
                    artistCountMap.put(artistName, artistCountMap.getOrDefault(artistName, 0) + 1);
                }
            }
        }

        System.out.println(artistCountMap.size() + " artists after " + date);

        printMap(artistCountMap);
        topNArtists(artistCountMap);

    }

    // prints a hash map in a formatted manner
    public static void printMap(Map<String, Integer> songCountMap){
        songCountMap = sortByValue(songCountMap, true);
        ArrayList<Integer> songs = new ArrayList<>(songCountMap.values());
        ArrayList<String> songTitles = new ArrayList<>(songCountMap.keySet());

        for (int x = 0; x < N; x++){
            System.out.println((x+1) + ".  " + songTitles.get(x) + " :: " + songs.get(x));
        }
        System.out.println();
    }

    // sorts map of artists / song titles by their count values either ascending or descending
    public static Map<String, Integer> sortByValue(Map<String, Integer> map, boolean descending) {

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
    public static void topNArtists(Map<String, Integer> artistCountMap){
        artistCountMap = sortByValue(artistCountMap, true);
        ArrayList<String> songs = new ArrayList<>(artistCountMap.keySet());
        for (int x = 0; x < N; x++) {
            getTopNSongs(songs.get(x));
        }
    }

    // Method to get the top N songs for a given artist
    public static void getTopNSongs(String artistName) {
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

    // getters and setters for date
    public static void setDate(String date){
        AnalyzerEngine.date = date;
    }

    public static String getDate(){
        return AnalyzerEngine.date;
    }

    // getters and setters for N
    public static void setN(int N){ AnalyzerEngine.N = N; }

    public static int getN(){ return AnalyzerEngine.N; }

}
