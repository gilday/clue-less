package edu.jhu.ep.butlerdidit.service;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import edu.jhu.ep.butlerdidit.service.api.GSMatch;

/**
 * Just used to serialize only the fields we want with GSON
 * The fields we want serialized when updating a match is 
 * different than the fields we want deserialized when 
 * receiving a match
 */
class UpdateMatchModel implements Parcelable {
	
	private int id;
	private String status;
	private String message;
	@SerializedName("match_data")
	private String matchData;
	@SerializedName("current_player")
	private String current_player;
	
	public UpdateMatchModel() { }

	public UpdateMatchModel(GSMatch entity) {
		id = entity.getId();
		status = entity.getStatus();
		message = entity.getMessage();
		matchData = entity.getRawMatchData();
		current_player = entity.getCurrentPlayer();
	}
	
	public UpdateMatchModel(Parcel in) {
		id = in.readInt();
		status = in.readString();
		message = in.readString();
		matchData = in.readString();
		current_player = in.readString();
	}
	
	public static final Parcelable.Creator<UpdateMatchModel> CREATOR = new Parcelable.Creator<UpdateMatchModel>() {

		public UpdateMatchModel createFromParcel(Parcel in) {
			return new UpdateMatchModel(in);
		}
		
		public UpdateMatchModel[] newArray(int size) {
			return new UpdateMatchModel[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeString(status);
		dest.writeString(message);
		dest.writeString(matchData);
		dest.writeString(current_player);
	}

	int getId() {
		return id;
	}

	String getStatus() {
		return status;
	}

	String getMessage() {
		return message;
	}

	String getMatchData() {
		return matchData;
	}

	String getCurrent_player() {
		return current_player;
	}
	
}
