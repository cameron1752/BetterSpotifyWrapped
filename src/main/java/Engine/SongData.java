package Engine;

public class SongData {
    private int plays;
    private String title;
    private String artist;
    private String album;

    public SongData(int plays, String title, String artist, String album) {
        this.plays = plays;
        this.title = title;
        this.artist = artist;
        this.album = album;
    }

    public int getPlays() {
        return plays;
    }

    public void setPlays(int plays) {
        this.plays = plays;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    @Override
    public String toString() {
        return "SongData{" +
                "plays=" + plays +
                ", title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                '}';
    }
}
