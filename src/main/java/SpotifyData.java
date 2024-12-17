import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.*;

public class SpotifyData {
    private String ts; // Timestamp in the format "YYY-MM-DD HH:mm:ss"
    private String username;
    private String platform;
    private Long msPlayed;
    private String connCountry;
    private String ipAddrDecrypted;
    private String userAgentDecrypted;
    private String masterMetadataTrackName;
    private String masterMetadataAlbumArtistName;
    private String masterMetadataAlbumName;
    private String spotifyTrackUri;
    private String episodeName;
    private String episodeShowName;
    private String spotifyEpisodeUri;
    private String reasonStart;
    private String reasonEnd;
    private Boolean shuffle;
    private Boolean skipped;
    private Boolean offline;
    private Long offlineTimestamp;

    // Getters and Setters
    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Long getMsPlayed() {
        return msPlayed;
    }

    public void setMsPlayed(Long msPlayed) {
        this.msPlayed = msPlayed;
    }

    public String getConnCountry() {
        return connCountry;
    }

    public void setConnCountry(String connCountry) {
        this.connCountry = connCountry;
    }

    public String getIpAddrDecrypted() {
        return ipAddrDecrypted;
    }

    public void setIpAddrDecrypted(String ipAddrDecrypted) {
        this.ipAddrDecrypted = ipAddrDecrypted;
    }

    public String getUserAgentDecrypted() {
        return userAgentDecrypted;
    }

    public void setUserAgentDecrypted(String userAgentDecrypted) {
        this.userAgentDecrypted = userAgentDecrypted;
    }

    public String getMasterMetadataTrackName() {
        return masterMetadataTrackName;
    }

    public void setMasterMetadataTrackName(String masterMetadataTrackName) {
        this.masterMetadataTrackName = masterMetadataTrackName;
    }

    public String getMasterMetadataAlbumArtistName() {
        return masterMetadataAlbumArtistName;
    }

    public void setMasterMetadataAlbumArtistName(String masterMetadataAlbumArtistName) {
        this.masterMetadataAlbumArtistName = masterMetadataAlbumArtistName;
    }

    public String getMasterMetadataAlbumName() {
        return masterMetadataAlbumName;
    }

    public void setMasterMetadataAlbumName(String masterMetadataAlbumName) {
        this.masterMetadataAlbumName = masterMetadataAlbumName;
    }

    public String getSpotifyTrackUri() {
        return spotifyTrackUri;
    }

    public void setSpotifyTrackUri(String spotifyTrackUri) {
        this.spotifyTrackUri = spotifyTrackUri;
    }

    public String getEpisodeName() {
        return episodeName;
    }

    public void setEpisodeName(String episodeName) {
        this.episodeName = episodeName;
    }

    public String getEpisodeShowName() {
        return episodeShowName;
    }

    public void setEpisodeShowName(String episodeShowName) {
        this.episodeShowName = episodeShowName;
    }

    public String getSpotifyEpisodeUri() {
        return spotifyEpisodeUri;
    }

    public void setSpotifyEpisodeUri(String spotifyEpisodeUri) {
        this.spotifyEpisodeUri = spotifyEpisodeUri;
    }

    public String getReasonStart() {
        return reasonStart;
    }

    public void setReasonStart(String reasonStart) {
        this.reasonStart = reasonStart;
    }

    public String getReasonEnd() {
        return reasonEnd;
    }

    public void setReasonEnd(String reasonEnd) {
        this.reasonEnd = reasonEnd;
    }

    public Boolean getShuffle() {
        return shuffle;
    }

    public void setShuffle(Boolean shuffle) {
        this.shuffle = shuffle;
    }

    public Boolean getSkipped() {
        return skipped;
    }

    public void setSkipped(Boolean skipped) {
        this.skipped = skipped;
    }

    public Boolean getOffline() {
        return offline;
    }

    public void setOffline(Boolean offline) {
        this.offline = offline;
    }

    public Long getOfflineTimestamp() {
        return offlineTimestamp;
    }

    public void setOfflineTimestamp(Long offlineTimestamp) {
        this.offlineTimestamp = offlineTimestamp;
    }

    @Override
    public String toString() {
        return "SpotifyData{" +
                "ts='" + ts + '\'' +
                ", username='" + username + '\'' +
                ", platform='" + platform + '\'' +
                ", msPlayed=" + msPlayed +
                ", connCountry='" + connCountry + '\'' +
                ", ipAddrDecrypted='" + ipAddrDecrypted + '\'' +
                ", userAgentDecrypted='" + userAgentDecrypted + '\'' +
                ", masterMetadataTrackName='" + masterMetadataTrackName + '\'' +
                ", masterMetadataAlbumArtistName='" + masterMetadataAlbumArtistName + '\'' +
                ", masterMetadataAlbumName='" + masterMetadataAlbumName + '\'' +
                ", spotifyTrackUri='" + spotifyTrackUri + '\'' +
                ", episodeName='" + episodeName + '\'' +
                ", episodeShowName='" + episodeShowName + '\'' +
                ", spotifyEpisodeUri='" + spotifyEpisodeUri + '\'' +
                ", reasonStart='" + reasonStart + '\'' +
                ", reasonEnd='" + reasonEnd + '\'' +
                ", shuffle=" + shuffle +
                ", skipped=" + skipped +
                ", offline=" + offline +
                ", offlineTimestamp=" + offlineTimestamp +
                '}';
    }

    // Method to read a list of JSON files and populate SpotifyData objects
    public static List<SpotifyData> readJsonFiles(List<String> filePaths) {
        List<SpotifyData> dataList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String filePath : filePaths) {
            try {
                String content = Files.readString(Paths.get(filePath));
                SpotifyData data = objectMapper.readValue(content, SpotifyData.class);
                dataList.add(data);
            } catch (IOException e) {
                System.err.println("Error reading or parsing file " + filePath + ": " + e.getMessage());
            }
        }

        return dataList;
    }
}
