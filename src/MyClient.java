
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import java.sql.*;
import java.io.File;

public class MyClient {

    public static String userSignup(String username, String useremail, String password, String phoneno, String address, File photo) {
        String ans = "";
        try {

            ResultSet rs = DBloader.executeSQL("select * from user where useremail='" + useremail + "' ");
            if (rs.next()) {
                ans = "fail";
            } else {

                String whole_photo = SaveFile.saveFile(photo);

                rs.moveToInsertRow();
                rs.updateString("username", username);
                rs.updateString("useremail", useremail);
                rs.updateString("password", password);
                rs.updateString("phoneno", phoneno);
                rs.updateString("address", address);
                rs.updateString("photo", whole_photo);

                rs.insertRow();
                ans = "success";
            }

        } catch (Exception e) {
            e.printStackTrace();
            ans = e.toString();
        }
        return ans;
    }
    
    public static String userLogin(String useremail, String password) {
        String ans = "";
        String username = "";
        String phoneno = "";
        String address = "";
        String photo = "";
        try {

            ResultSet rs = DBloader.executeSQL("select * from user where useremail='" + useremail + "' and password='" + password + "' ");
            if (rs.next()) {
                ans = "success";

                username = rs.getString("username");
                phoneno = rs.getString("phoneno");
                address = rs.getString("address");
                photo = rs.getString("photo");

                global.username = username;
                global.useremail = useremail;
                global.phoneno = phoneno;
                global.address = address;
                global.photo = photo;
            } else {
                ans = "Useremail or password is incorrect.";
            }
        } catch (Exception e) {
            e.printStackTrace();
            ans = e.toString();
        }
        return ans;
    }

    public static String addUpcomingToFav(int id) {
        String ans = "";
        try {
            String useremail = global.useremail;
            ResultSet rs = DBloader.executeSQL("select * from favmovies where useremail= '" + useremail + "' and id='" + id + "'  ");
            if (rs.next()) {
                rs.deleteRow();
                ans = "remove";
            } else {
                rs.moveToInsertRow();
                rs.updateString("useremail", useremail);
                rs.updateInt("id", id);
                rs.insertRow();

                ans = "add";
            }

        } catch (Exception e) {
            e.printStackTrace();
            ans = e.toString();
        }
        return ans;
    }

    public static String checkButton(int id) {
        String ans = "";
        try {
            String useremail = global.useremail;
            ResultSet rs = DBloader.executeSQL("select * from favmovies where useremail='" + useremail + "' and id='" + id + "' ");
            if (rs.next()) {
                ans = "success";
            }

        } catch (Exception e) {
            e.printStackTrace();
            ans = e.toString();
        }
        return ans;
    }

    public static String addTvToFav(int id) {
        String ans = "";
        try {
            String useremail = global.useremail;
            ResultSet rs = DBloader.executeSQL("select * from favtv where useremail= '" + useremail + "' and id='" + id + "'  ");
            if (rs.next()) {
                rs.deleteRow();
                ans = "remove";
            } else {
                rs.moveToInsertRow();
                rs.updateString("useremail", useremail);
                rs.updateInt("id", id);
                rs.insertRow();

                ans = "add";
            }

        } catch (Exception e) {
            e.printStackTrace();
            ans = e.toString();
        }
        return ans;
    }

    public static String checkButtonTv(int id) {
        String ans = "";
        try {
            String useremail = global.useremail;
            ResultSet rs = DBloader.executeSQL("select * from favtv where useremail='" + useremail + "' and id='" + id + "' ");
            if (rs.next()) {
                ans = "success";
            }

        } catch (Exception e) {
            e.printStackTrace();
            ans = e.toString();
        }
        return ans;
    }

    public static String editPhoto(File photo) {
        String ans = "";
        String useremail = global.useremail;
        try {

            ResultSet rs = DBloader.executeSQL("select * from user where useremail='" + useremail + "' ");
            if (rs.next()) {
                String whole_photo = SaveFile.saveFile(photo);
                rs.updateString("photo", whole_photo);
                rs.updateRow();
                global.photo = whole_photo;
                ans = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            ans = e.toString();
        }
        return ans;
    }

    public static String editInfo(String username, String phoneno, String address) {
        String ans = "";
        String useremail = global.useremail;
        try {

            ResultSet rs = DBloader.executeSQL("select * from user where useremail='" + useremail + "' ");
            if (rs.next()) {
                rs.updateString("username", username);
                rs.updateString("phoneno", phoneno);
                rs.updateString("address", address);
                rs.updateRow();

                global.username = username;
                global.phoneno = phoneno;
                global.address = address;

                ans = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            ans = e.toString();
        }
        return ans;
    }

    public static String userChangePassword(String opassword, String npassword) {
        String ans = "";
        String useremail = global.useremail;
        try {

            ResultSet rs = DBloader.executeSQL("select * from user where useremail='" + useremail + "' and password='" + opassword + "' ");
            if (rs.next()) {

                rs.updateString("password", npassword);
                rs.updateRow();
                ans = "success";
            } else {
                ans = "Old Password Is Incorrect";
            }

        } catch (Exception e) {
            e.printStackTrace();
            ans = e.toString();
        }
        return ans;
    }

    public static String deleteAccount() {
        String useremail = global.useremail;
        String ans = "";
        try {
            ResultSet rs = DBloader.executeSQL("select * from user where useremail='" + useremail + "' ");
            if (rs.next()) {
                rs.deleteRow();

                global.address = "";
                global.phoneno = "";
                global.photo = "";
                global.useremail = "";
                global.username = "";

                ans = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            ans = e.toString();
        }

        return ans;
    }

    public static String UpcomingMovies() {
        String api = "980d96176457a6e65b8bc282bcadccd4";
        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/movie/upcoming?api_key=" + api + "&language=en-US&page=1")
                    .asString();
            //System.out.println(response.getBody());
            if (response.getStatus() == 200) {
                String res = response.getBody();

                return res;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
        return null;
    }

    public static String searchActor(String actorname) {

        String api = "980d96176457a6e65b8bc282bcadccd4";

        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/person?query=" + actorname + "&include_adult=false&language=en-US&page=1&api_key=" + api).asString();
            //System.out.println(response.getBody());
            if (response.getStatus() == 200) {
                String res = response.getBody();

                return res;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
        return null;
    }

    public static String topTrending() {

        String api = "980d96176457a6e65b8bc282bcadccd4";

        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/trending/all/day?api_key=" + api).asString();
            //System.out.println(response.getBody());
            if (response.getStatus() == 200) {
                String res = response.getBody();

                return res;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
        return null;
    }

    public static String searchMovie(String movie) {
        String api = "980d96176457a6e65b8bc282bcadccd4";

        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/movie?api_key=" + api + "&language=en-US&query=" + movie + "&page=1&include_adult=false").asString();
            //System.out.println(response.getBody());
            if (response.getStatus() == 200) {
                String res = response.getBody();

                return res;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
        return null;
    }

    public static String searchTVShow(String tv) {
        String api = "980d96176457a6e65b8bc282bcadccd4";

        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/tv?api_key=" + api + "&language=en-US&page=1&query=" + tv + "&include_adult=false").asString();
            //System.out.println(response.getBody());
            if (response.getStatus() == 200) {
                String res = response.getBody();

                return res;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
        return null;
    }

    public static String searchMovieHouse(String house) {
        String api = "980d96176457a6e65b8bc282bcadccd4";

        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/company?api_key=" + api + "&query=" + house + "&page=1").asString();
            //System.out.println(response.getBody());
            if (response.getStatus() == 200) {
                String res = response.getBody();

                return res;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
        return null;
    }
    
        public static String searchMulti(String search) {
        String api = "980d96176457a6e65b8bc282bcadccd4";
        try {
            HttpResponse<String> response = Unirest.get("https://api.themoviedb.org/3/search/multi?api_key=" + api + "&language=en-US&query=" + search + "&page=1&include_adult=false").asString();
            //System.out.println(response.getBody());
            if (response.getStatus() == 200) {
                String res = response.getBody();

                return res;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
        return null;
    }

    public static String renderFavMovies() {
        String ans = "";
        try {
            String useremail = global.useremail;
            ResultSet rs = DBloader.executeSQL("select * from favmovies where useremail='" + useremail + "' ");
            while (rs.next()) {
                int id = rs.getInt("id");
                ans += id + ";";
            }
            return ans;
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }
    
    
    public static String renderFavTV() {
        String ans = "";
        try {
            String useremail = global.useremail;
            ResultSet rs = DBloader.executeSQL("select * from favtv where useremail='" + useremail + "' ");
            while (rs.next()) {
                int id = rs.getInt("id");
                ans += id + ";";
            }
            return ans;
        } catch (Exception ex) {
            ex.printStackTrace();
            return ex.toString();
        }
    }

}
