package com.red_spark.redsparkdev.moviestalker.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Red_Spark on 08-Aug-17.
 *  * JSON data converted into a java class via GSON
 */

public class ItemData implements Serializable, Parcelable {

    public String getPage() {return page;}
    public String getTotal_results() {return total_results;}
    public String getTotal_pages() {return total_pages;}
    public List<Result> getResults() {return results;}

    private String page;
    private String total_results;
    private String total_pages;

    public Constants.DATA_TYPE getDataType() {return dataType;}
    public void setDataType(Constants.DATA_TYPE dataType) {this.dataType = dataType;}
    private Constants.DATA_TYPE dataType;

    public List<Result> results = new ArrayList<>();
    public class Result implements Serializable, Parcelable {
        public String getVote_count() {return vote_count;}
        public String getID() {return id;}
        public String getVideo() {return video;}
        public String getVote_average() {return vote_average;}
        public String getTitle() {
            //If the object is holding tv_series data the title is store in the name field;
            if(title == null || title.isEmpty())
                return name;
            return title;
        }
        public String getPopularity() {return popularity;}
        public String getPoster_path() {return poster_path;}
        public String getOriginal_language() {return original_language;}
        public String getOriginal_title() {
            if(original_title == null || original_title.isEmpty())
                return original_name;
            return original_title;
        }
        public List<String> getGenre_ids() {return genre_ids;}
        public String getBackdrop_path() {return backdrop_path;}
        public String getAdult() {return adult;}
        public String getOverview() {return overview;}
        public String getRelease_date() {
            if(release_date == null || release_date.isEmpty())
                return first_air_date;
            return release_date;
        }
        public Constants.DATA_TYPE getDataType(){
            if(title == null || title.isEmpty())
                return Constants.DATA_TYPE.SERIES;
            return Constants.DATA_TYPE.MOVIES;
        }

        String vote_count;
        String id;
        String video;
        String vote_average;
        String title;//used in movies
        String name;//used in tv_series
        String popularity;
        String poster_path;
        String original_language;
        String original_title;
        String original_name;
        List<String> genre_ids = new ArrayList<>();
        String backdrop_path;
        String adult;
        String overview;
        String release_date;
        String first_air_date;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.vote_count);
            dest.writeString(this.id);
            dest.writeString(this.video);
            dest.writeString(this.vote_average);
            dest.writeString(this.title);
            dest.writeString(this.name);
            dest.writeString(this.popularity);
            dest.writeString(this.poster_path);
            dest.writeString(this.original_language);
            dest.writeString(this.original_title);
            dest.writeString(this.original_name);
            dest.writeStringList(this.genre_ids);
            dest.writeString(this.backdrop_path);
            dest.writeString(this.adult);
            dest.writeString(this.overview);
            dest.writeString(this.release_date);
            dest.writeString(this.first_air_date);
        }

        public Result() {
        }

        protected Result(Parcel in) {
            this.vote_count = in.readString();
            this.id = in.readString();
            this.video = in.readString();
            this.vote_average = in.readString();
            this.title = in.readString();
            this.name = in.readString();
            this.popularity = in.readString();
            this.poster_path = in.readString();
            this.original_language = in.readString();
            this.original_title = in.readString();
            this.original_name = in.readString();
            this.genre_ids = in.createStringArrayList();
            this.backdrop_path = in.readString();
            this.adult = in.readString();
            this.overview = in.readString();
            this.release_date = in.readString();
            this.first_air_date = in.readString();
        }

        public final Creator<Result> CREATOR = new Creator<Result>() {
            @Override
            public Result createFromParcel(Parcel source) {
                return new Result(source);
            }

            @Override
            public Result[] newArray(int size) {
                return new Result[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.page);
        dest.writeString(this.total_results);
        dest.writeString(this.total_pages);
        dest.writeInt(this.dataType == null ? -1 : this.dataType.ordinal());
        dest.writeList(this.results);
    }

    public ItemData() {
    }

    protected ItemData(Parcel in) {
        this.page = in.readString();
        this.total_results = in.readString();
        this.total_pages = in.readString();
        int tmpDataType = in.readInt();
        this.dataType = tmpDataType == -1 ? null : Constants.DATA_TYPE.values()[tmpDataType];
        this.results = new ArrayList<Result>();
        in.readList(this.results, Result.class.getClassLoader());
    }

    public static final Parcelable.Creator<ItemData> CREATOR = new Parcelable.Creator<ItemData>() {
        @Override
        public ItemData createFromParcel(Parcel source) {
            return new ItemData(source);
        }

        @Override
        public ItemData[] newArray(int size) {
            return new ItemData[size];
        }
    };
}
