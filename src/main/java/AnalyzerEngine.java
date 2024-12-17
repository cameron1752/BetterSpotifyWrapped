import java.util.*;
import java.util.stream.Collectors;

public class AnalyzerEngine {

    public static List<SpotifyData> data = new ArrayList<>();
    public static String date;

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

        System.out.println(count + " songs after " + date);

        printMap(songCountMap);

    }

    public static void topArtistsByYear(){
        Map<String, Integer> songCountMap = new HashMap<>();
        int count = 0;

        for (SpotifyData sd : data) {
            if (sd.isAfter(date)){
                String artistName = sd.getMasterMetadataAlbumArtistName();

                if (artistName != null && !artistName.isEmpty()) {
                    songCountMap.put(artistName, songCountMap.getOrDefault(artistName, 0) + 1);
                }
                count++;
            }
        }

        System.out.println(count + " artists after " + date);

        printMap(songCountMap);

    }

    public static void printMap(Map<String, Integer> songCountMap){
        songCountMap = sortByValue(songCountMap, true);
        ArrayList<Integer> songs = new ArrayList<>(songCountMap.values());
        ArrayList<String> songTitles = new ArrayList<>(songCountMap.keySet());

        for (int x = 0; x < 5; x++){
            System.out.println((x+1) + ".  " + songTitles.get(x) + " :: " + songs.get(x));
        }
        System.out.println();
    }

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

    public static void setDate(String date){
        AnalyzerEngine.date = date;
    }

    public static String getDate(){
        return AnalyzerEngine.date;
    }

}
