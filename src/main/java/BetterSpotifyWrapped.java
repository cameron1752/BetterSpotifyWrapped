public class BetterSpotifyWrapped {

    public static void main( String[] args ){
        String date = "2023-12-31T23:59:59Z";
//        String date = "2010-12-31T23:59:59Z";
        AnalyzerEngine.init(date);

        AnalyzerEngine.topSongsByYear();
        AnalyzerEngine.topArtistsByYear();

        // todo: shortest gap between songs
        // todo: longest gap between songs
    }
}
