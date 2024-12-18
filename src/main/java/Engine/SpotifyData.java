package Engine;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SpotifyData {
    @JsonProperty("ts")
    private String ts; // Timestamp in the format "YYY-MM-DD HH:mm:ss"
    @JsonProperty("username")
    private String username;
    @JsonProperty("platform")
    private String platform;
    @JsonProperty("ms_played")
    private Long msPlayed;
    @JsonProperty("conn_country")
    private String connCountry;
    @JsonProperty("ip_addr")
    private String ipAddrDecrypted;
    @JsonProperty("master_metadata_track_name")
    private String masterMetadataTrackName;
    @JsonProperty("master_metadata_album_artist_name")
    private String masterMetadataAlbumArtistName;
    @JsonProperty("master_metadata_album_album_name")
    private String masterMetadataAlbumName;
    @JsonProperty("spotify_track_uri")
    private String spotifyTrackUri;
    @JsonProperty("episode_name")
    private String episodeName;
    @JsonProperty("episode_show_name")
    private String episodeShowName;
    @JsonProperty("spotify_episode_uri")
    private String spotifyEpisodeUri;
    @JsonProperty("reason_start")
    private String reasonStart;
    @JsonProperty("reason_end")
    private String reasonEnd;
    @JsonProperty("shuffle")
    private Boolean shuffle;
    @JsonProperty("skipped")
    private Boolean skipped;
    @JsonProperty("offline")
    private Boolean offline;
    @JsonProperty("offline_timestamp")
    private Long offlineTimestamp;
    @JsonProperty("incognito_mode")
    private Boolean incognitoMode;


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

    public Boolean getOnline() {
        return incognitoMode;
    }

    public void setOnline(Boolean incognitoMode) {
        this.incognitoMode = incognitoMode;
    }

    @Override
    public String toString() {
        return "Engine.SpotifyData{" +
                "ts='" + ts + '\'' +
                ", username='" + username + '\'' +
                ", platform='" + platform + '\'' +
                ", msPlayed=" + msPlayed +
                ", connCountry='" + connCountry + '\'' +
                ", ipAddrDecrypted='" + ipAddrDecrypted + '\'' +
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

    // Method to read a list of JSON files and populate Engine.SpotifyData objects
    public static List<SpotifyData> readJsonFiles(List<String> filePaths) {
        List<SpotifyData> dataList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String filePath : filePaths) {
            try {
                String content = Files.readString(Paths.get(filePath));
                List<SpotifyData> data = objectMapper.readValue(content, new TypeReference<List<SpotifyData>>() {});
                dataList.addAll(data);
            } catch (IOException e) {
                System.err.println("Error reading or parsing file " + filePath + ": " + e.getMessage());
            }
        }

        return dataList;
    }

    // Method to convert the ts field from UTC to EST
    public String convertTsToEST() {
        if (this.ts != null && !this.ts.isEmpty()) {
            // Adjust for ISO-8601 format with 'Z' indicating UTC
            DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME; // Handles "2024-12-15T22:12:53Z"
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime utcTime = LocalDateTime.parse(this.ts, isoFormatter); // Parse ISO-8601
            LocalDateTime estTime = utcTime.atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of("America/New_York"))
                    .toLocalDateTime();
            return estTime.format(outputFormatter); // Format back to desired output
        }
        return "";
    }

    // method to determine if date is after start date
    public boolean isAfter(String ts){
        if (this.ts != null && !this.ts.isEmpty()) {
            // Adjust for ISO-8601 format with 'Z' indicating UTC
            DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_DATE_TIME; // Handles "2024-12-15T22:12:53Z"
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            LocalDateTime utcTime = LocalDateTime.parse(this.ts, isoFormatter); // Parse ISO-8601
            LocalDateTime estTime = utcTime.atZone(ZoneId.of("UTC"))
                    .withZoneSameInstant(ZoneId.of("America/New_York"))
                    .toLocalDateTime();
             // Format back to desired output
            return estTime.isAfter(LocalDateTime.parse(ts, isoFormatter));
        }
        return false;
    }
}
