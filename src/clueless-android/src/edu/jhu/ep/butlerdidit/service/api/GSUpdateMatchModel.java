package edu.jhu.ep.butlerdidit.service.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


/**
 * Just used to serialize only the fields we want with GSON
 * The fields we want serialized when updating a match is 
 * different than the fields we want deserialized when 
 * receiving a match
 */
public class GSUpdateMatchModel implements Parcelable {
	
	private int id;
	private String status;
	private String message;
	@SerializedName("match_data")
	private String matchData;
	@SerializedName("current_player")
	private String currentPlayer;
	
	public GSUpdateMatchModel() { }

	public GSUpdateMatchModel(GSMatch entity) {
		id = entity.getId();
		status = entity.getStatus();
		message = entity.getMessage();
		matchData = entity.getRawMatchData();
		currentPlayer = entity.getCurrentPlayer();
	}
	
	public GSUpdateMatchModel(Parcel in) {
		id = in.readInt();
		status = in.readString();
		message = in.readString();
		matchData = in.readString();
		currentPlayer = in.readString();
	}
	
	public static final Parcelable.Creator<GSUpdateMatchModel> CREATOR = new Parcelable.Creator<GSUpdateMatchModel>() {

		public GSUpdateMatchModel createFromParcel(Parcel in) {
			return new GSUpdateMatchModel(in);
		}
		
		public GSUpdateMatchModel[] newArray(int size) {
			return new GSUpdateMatchModel[size];
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
		dest.writeString(currentPlayer);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMatchData() {
		return matchData;
	}

	public void setMatchData(String matchData) {
		this.matchData = matchData;
	}

	public String getCurrentPlayer() {
		return currentPlayer;
	}

	public void setCurrentPlayer(String currentPlayer) {
		this.currentPlayer = currentPlayer;
	}
}
