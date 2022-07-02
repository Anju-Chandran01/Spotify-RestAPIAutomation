package com.bridgelabz.spotify;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class SpotifyApi {

    public String token;
    public String userId;
    public String playlistId;
    public String trackId;

    @BeforeTest
    public void getTokenAndIds() {
        token = "Bearer BQAom97Coo-9mg1JFenTbnGX7Jm7lpky1izoB2Wu1d7UgtaBB6Q4Xi7crsZr_PZxCtH61L7ofv3or1IWrnbDkq2eA67JhWwCaf4LGoqCkyV2VfgCes8ICsRjoiA0RXMbqJppIneKo7YrY9xY6hbLK5MnJfOG3EWmixdSenKRA9Q89DHlaj52-A0W69GocsSl7ISgkOD-wZTzo9RfpOTs_TcZPUR9JDmy0Po_6LPoRKfMwsUqU4yAbWCaB-OPrbWa7KBnwrrO83xdqG7lIYxrKVgAFeSYac9n-aCLtjcjXjNrygnYeD1WExgEq8LmMoZl";
        userId = "31x375imhz4wrp4h5kcnf7ssc5iy";
        playlistId = "4JF8MLAo2GXD4nexIsMdli";
        trackId = "5O932cZmzOZGOGZz9RHx20";
    }

    //    GET CURRENT USERS PROFILE
    @Test(priority = 0)
    public void testGet_CurrentUsersProfile_ShouldReturn_StatusCode200() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/me");
        response.prettyPrint();
        userId = response.path("id");
        System.out.println("User ID : " + userId);
        response.then().assertThat().statusCode(200);
    }

    //     GET USERS PROFILE USING USERID
    @Test(priority = 1)
    public void Get_UserProfile(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization",token)
                .when()
                .get("https://api.spotify.com/v1/users/" + userId + "");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // CREATE PLAYLIST
    @Test(priority = 2)
    public void post_CreatePlaylist() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        "  \"name\": \"Spotify's Automated Playlist\",\n" +
                        "  \"description\": \"Automated playlist of Spotify\",\n" +
                        "  \"public\": false\n" +
                        "}")
                .when()
                .post("https://api.spotify.com/v1/users/" + userId + "/playlists");
        response.prettyPrint();
        playlistId = response.path("id");
        System.out.println("Playlist ID : " + playlistId);
        response.then().assertThat().statusCode(201);
    }

    // GET USERS PLAYLIST
    @Test (priority = 3)
    public void get_UsersPlaylist(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/users/" + userId + "/playlists");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // SEARCH FOR ITEM
    @Test(priority = 4)
    public void get_SearchForItem(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .queryParam("q","Selena Gomez")
                .queryParam("type","track")
                .when()
                .get("https://api.spotify.com/v1/search");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // ADD ITEMS TO THE PLAYLIST
    @Test(priority = 5)
    public void post_AddItemsToPlaylist_ShouldReturn_StatusCode201() {
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .queryParam("uris", "spotify:track:3TcL0dyCMyr0kyTTc4NLgI,spotify:track:4Zzw8CESa97fNrmrvEdIuL,spotify:track:06KyNuuMOX1ROXRhj787tj")
                .when()
                .post("https://api.spotify.com/v1/playlists/" +playlistId+ "/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(201);
    }

    // GET PLAYLIST COVER IMAGE
    @Test(priority = 6)
    public void get_PlaylistCoverImage_ShouldReturn_StatusCode200(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/playlists/" + playlistId + "/images");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

//    // GET PLAYLIST
    @Test(priority = 7)
    public void get_Playlist_ShouldReturn_StatusCode200(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/playlists/" + playlistId);
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

     // GET CURRENT USERS PLAYLIST
    @Test(priority = 8)
    public void get_CurrentUsersPlaylist_ShouldReturn_StatusCode200(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/me/playlists");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // GET PLAYLIST ITEMS
    @Test(priority = 9)
    public void get_PlaylistItems_ShouldReturn_StatusCode200(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/playlists/" + playlistId + "/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // UPDATE PLAYLIST ITEM
    @Test(priority = 10)
    public void update_PlaylistItem_ShouldReturn_StatusCode200(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        "  \"range_start\": 1,\n" +
                        "  \"insert_before\": 3,\n" +
                        "  \"range_length\": 1\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/" +playlistId+ "/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // CHANGE PLAYLIST DETAILS
    @Test (priority = 11)
    public void change_PlaylistDetails(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .body("{\n" +
                        "  \"name\": \"Song's of Selena \",\n" +
                        "  \"description\": \"Updated playlist description\",\n" +
                        "  \"public\": false\n" +
                        "}")
                .when()
                .put("https://api.spotify.com/v1/playlists/" + playlistId);
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // GET SEVERAL TRACK
    @Test(priority = 12)
    public void get_SeveralTasks_ShouldReturn_StatusCode200(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .queryParam("ids","4Zzw8CESa97fNrmrvEdIu,06KyNuuMOX1ROXRhj787tj,3TcL0dyCMyr0kyTTc4NLgI")
                .get("https://api.spotify.com/v1/playlists/" + playlistId);
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }


    // GET TRACKS
    @Test(priority = 13)
    public void get_Tracks_ShouldReturn_StatusCode200(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/playlists/"+playlistId);
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // GET TRACKS AUDIO ANALYSIS
    @Test (priority = 14)
    public void get_TrackAudioAnalysis_ShouldReturn_StatusCode200(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/audio-analysis/" + trackId);
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // GET TRACKS AUDIO FEATURES
    @Test (priority = 15)
    public void get_TrackAudioFeatures_ShouldReturn_StatusCode200(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/audio-features");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // GET AUDIO FEATURES OF PARTICULAR ID
    @Test (priority = 16)
    public void get_TrackAudioFeature_ShouldReturn_StatusCode200(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .when()
                .get("https://api.spotify.com/v1/audio-features/" + trackId);
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    // DELETE PLAYLIST ITEMS
    @Test(priority = 17)
    public void delete_PlaylistItems_ShouldReturn_StatusCode200(){
        Response response = RestAssured.given().contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", token)
                .body("{ \"tracks\": [{ \"uri\": \"spotify:track:4Zzw8CESa97fNrmrvEdIuL\" }] }")
                .when()
                .delete("https://api.spotify.com/v1/playlists/" + playlistId + "/tracks");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
}
