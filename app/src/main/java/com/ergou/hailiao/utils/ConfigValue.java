package com.ergou.hailiao.utils;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfigValue implements Parcelable {
	public String getIndexImage11() {
		return indexImage11;
	}

	public void setIndexImage11(String indexImage11) {
		this.indexImage11 = indexImage11;
	}

	public String getIndexImage22() {
		return indexImage22;
	}

	public void setIndexImage22(String indexImage22) {
		this.indexImage22 = indexImage22;
	}

	public String getIndexImage33() {
		return indexImage33;
	}

	public void setIndexImage33(String indexImage33) {
		this.indexImage33 = indexImage33;
	}

	public String getIndexImage44() {
		return indexImage44;
	}

	public void setIndexImage44(String indexImage44) {
		this.indexImage44 = indexImage44;
	}

	private int id;
	private String serviceAgreement;
	private String assistColor;
	private String bgImage;
	private String mainColor;
	private String mainText1;
	private String mainText2;
	private String mainImage1;
	private String mainImage2;
	private String indexText1;
	private String indexText2;
	private String indexText3;
	private String indexText4;
	private String indexImage1;
	private String indexImage2;
	private String indexImage3;
	private String indexImage4;
	private String indexImage11;
	private String indexImage22;
	private String indexImage33;
	private String indexImage44;
	private String postText2;
	private String postText1;
	private String postImage1;
	private String postImage2;

	private String companyKey1;
	private String companyKey2;
	private String companyProduct1;
	private String companyProduct2;
	private String companyTag;
	private String configUuid;
	private String appName;
	private String appVersion;
	private String appIntroduce;

	public ConfigValue(String value) {
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(value);

			if (jsonObject.has("id")) {
				id = jsonObject.getInt("id");
			} else {
				id = -1;
			}

			if (jsonObject.has("companyKey1")) {
				companyKey1 = jsonObject.getString("companyKey1");
			} else {
				companyKey1 = "";
			}

			if (jsonObject.has("companyKey2")) {
				companyKey2 = jsonObject.getString("companyKey2");
			} else {
				companyKey2 = "";
			}

			if (jsonObject.has("companyProduct1")) {
				companyProduct1 = jsonObject.getString("companyProduct1");
			} else {
				companyProduct1 = "";
			}

			if (jsonObject.has("companyProduct2")) {
				companyProduct2 = jsonObject.getString("companyProduct2");
			} else {
				companyProduct2 = "";
			}

			if (jsonObject.has("companyTag")) {
				companyTag = jsonObject.getString("companyTag");
			} else {
				companyTag = "";
			}

			if (jsonObject.has("serviceAgreement")) {
				serviceAgreement = jsonObject.getString("serviceAgreement");
			} else {
				serviceAgreement = "";
			}

			if (jsonObject.has("assistColor")) {
				assistColor = jsonObject.getString("assistColor");
			} else {
				assistColor = "";
			}

			if (jsonObject.has("bgImage")) {
				bgImage = jsonObject.getString("bgImage");
			} else {
				bgImage = "";
			}

			if (jsonObject.has("mainColor")) {
				mainColor = jsonObject.getString("mainColor");
			} else {
				mainColor = "";
			}

			if (jsonObject.has("mainText1")) {
				mainText1 = jsonObject.getString("mainText1");
			} else {
				mainText1 = "";
			}

			if (jsonObject.has("mainText2")) {
				mainText2 = jsonObject.getString("mainText2");
			} else {
				mainText2 = "";
			}

			if (jsonObject.has("mainImage1")) {
				mainImage1 = jsonObject.getString("mainImage1");
			} else {
				mainImage1 = "";
			}

			if (jsonObject.has("mainImage2")) {
				mainImage2 = jsonObject.getString("mainImage2");
			} else {
				mainImage2 = "";
			}

			if (jsonObject.has("indexText1")) {
				indexText1 = jsonObject.getString("indexText1");
			} else {
				indexText1 = "";
			}

			if (jsonObject.has("indexText2")) {
				indexText2 = jsonObject.getString("indexText2");
			} else {
				indexText2 = "";
			}

			if (jsonObject.has("indexText3")) {
				indexText3 = jsonObject.getString("indexText3");
			} else {
				indexText3 = "";
			}

			if (jsonObject.has("indexText4")) {
				indexText4 = jsonObject.getString("indexText4");
			} else {
				indexText4 = "";
			}

			if (jsonObject.has("indexImage1")) {
				indexImage1 = jsonObject.getString("indexImage1");
			} else {
				indexImage1 = "";
			}

			if (jsonObject.has("indexImage2")) {
				indexImage2 = jsonObject.getString("indexImage2");
			} else {
				indexImage2 = "";
			}

			if (jsonObject.has("indexImage3")) {
				indexImage3 = jsonObject.getString("indexImage3");
			} else {
				indexImage3 = "";
			}

			if (jsonObject.has("indexImage4")) {
				indexImage4 = jsonObject.getString("indexImage4");
			} else {
				indexImage4 = "";
			}

			if (jsonObject.has("indexImage11")) {
				indexImage11 = jsonObject.getString("indexImage11");
			} else {
				indexImage11 = "";
			}

			if (jsonObject.has("indexImage22")) {
				indexImage22 = jsonObject.getString("indexImage22");
			} else {
				indexImage22 = "";
			}

			if (jsonObject.has("indexImage33")) {
				indexImage33 = jsonObject.getString("indexImage33");
			} else {
				indexImage33 = "";
			}

			if (jsonObject.has("indexImage44")) {
				indexImage44 = jsonObject.getString("indexImage44");
			} else {
				indexImage44 = "";
			}

			if (jsonObject.has("postText2")) {
				postText2 = jsonObject.getString("postText2");
			} else {
				postText2 = "";
			}

			if (jsonObject.has("postText1")) {
				postText1 = jsonObject.getString("postText1");
			} else {
				postText1 = "";
			}

			if (jsonObject.has("postImage1")) {
				postImage1 = jsonObject.getString("postImage1");
			} else {
				postImage1 = "";
			}

			if (jsonObject.has("postImage2")) {
				postImage2 = jsonObject.getString("postImage2");
			} else {
				postImage2 = "";
			}

			if (jsonObject.has("configUuid")) {
				configUuid = jsonObject.getString("configUuid");
			} else {
				configUuid = "";
			}

			if (jsonObject.has("appName")) {
				appName = jsonObject.getString("appName");
			} else {
				appName = "";
			}

			if (jsonObject.has("appVersion")) {
				appVersion = jsonObject.getString("appVersion");
			} else {
				appVersion = "";
			}

			if (jsonObject.has("appIntroduce")) {
				appIntroduce = jsonObject.getString("appIntroduce");
			} else {
				appIntroduce = "";
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCompanyKey1() {
		return companyKey1;
	}

	public void setCompanyKey1(String companyKey1) {
		this.companyKey1 = companyKey1;
	}

	public String getCompanyKey2() {
		return companyKey2;
	}

	public void setCompanyKey2(String companyKey2) {
		this.companyKey2 = companyKey2;
	}

	public String getCompanyProduct1() {
		return companyProduct1;
	}

	public void setCompanyProduct1(String companyProduct1) {
		this.companyProduct1 = companyProduct1;
	}

	public String getCompanyProduct2() {
		return companyProduct2;
	}

	public void setCompanyProduct2(String companyProduct2) {
		this.companyProduct2 = companyProduct2;
	}

	public String getCompanyTag() {
		return companyTag;
	}

	public void setCompanyTag(String companyTag) {
		this.companyTag = companyTag;
	}

	public ConfigValue(Parcel source) {
		id = source.readInt();
		serviceAgreement = source.readString();
		assistColor = source.readString();
		bgImage = source.readString();
		mainColor = source.readString();
		mainText1 = source.readString();
		mainText2 = source.readString();
		mainImage1 = source.readString();
		mainImage2 = source.readString();
		indexText1 = source.readString();
		indexText2 = source.readString();
		indexText3 = source.readString();
		indexText4 = source.readString();
		indexImage1 = source.readString();
		indexImage2 = source.readString();
		indexImage3 = source.readString();
		indexImage4 = source.readString();
		postText2 = source.readString();
		postText1 = source.readString();
		postImage1 = source.readString();
		postImage2 = source.readString();
		configUuid = source.readString();
		appName = source.readString();
		appVersion = source.readString();
		appIntroduce = source.readString();
	}

	public static final Creator<ConfigValue> CREATOR = new Creator<ConfigValue>() {
		@Override
		public ConfigValue createFromParcel(Parcel source) {
			return new ConfigValue(source);
		}

		@Override
		public ConfigValue[] newArray(int size) {
			return new ConfigValue[size];
		}
	};

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int arg1) {
		dest.writeInt(id);
		dest.writeString(serviceAgreement);
		dest.writeString(assistColor);
		dest.writeString(bgImage);
		dest.writeString(mainColor);
		dest.writeString(mainText1);
		dest.writeString(mainText2);
		dest.writeString(mainImage1);
		dest.writeString(mainImage2);
		dest.writeString(indexText1);
		dest.writeString(indexText2);
		dest.writeString(indexText3);
		dest.writeString(indexText4);
		dest.writeString(indexImage1);
		dest.writeString(indexImage2);
		dest.writeString(indexImage3);
		dest.writeString(indexImage4);
		dest.writeString(postText2);
		dest.writeString(postText1);
		dest.writeString(postImage1);
		dest.writeString(postImage2);
		dest.writeString(configUuid);
		dest.writeString(appName);
		dest.writeString(appVersion);
		dest.writeString(appIntroduce);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getServiceAgreement() {
		return serviceAgreement;
	}

	public void setServiceAgreement(String serviceAgreement) {
		this.serviceAgreement = serviceAgreement;
	}

	public String getAssistColor() {
		return assistColor;
	}

	public void setAssistColor(String assistColor) {
		this.assistColor = assistColor;
	}

	public String getBgImage() {
		return bgImage;
	}

	public void setBgImage(String bgImage) {
		this.bgImage = bgImage;
	}

	public String getMainColor() {
		return mainColor;
	}

	public void setMainColor(String mainColor) {
		this.mainColor = mainColor;
	}

	public String getMainText1() {
		return mainText1;
	}

	public void setMainText1(String mainText1) {
		this.mainText1 = mainText1;
	}

	public String getMainText2() {
		return mainText2;
	}

	public void setMainText2(String mainText2) {
		this.mainText2 = mainText2;
	}

	public String getMainImage1() {
		return mainImage1;
	}

	public void setMainImage1(String mainImage1) {
		this.mainImage1 = mainImage1;
	}

	public String getMainImage2() {
		return mainImage2;
	}

	public void setMainImage2(String mainImage2) {
		this.mainImage2 = mainImage2;
	}

	public String getIndexText1() {
		return indexText1;
	}

	public void setIndexText1(String indexText1) {
		this.indexText1 = indexText1;
	}

	public String getIndexText2() {
		return indexText2;
	}

	public void setIndexText2(String indexText2) {
		this.indexText2 = indexText2;
	}

	public String getIndexText3() {
		return indexText3;
	}

	public void setIndexText3(String indexText3) {
		this.indexText3 = indexText3;
	}

	public String getIndexText4() {
		return indexText4;
	}

	public void setIndexText4(String indexText4) {
		this.indexText4 = indexText4;
	}

	public String getIndexImage1() {
		return indexImage1;
	}

	public void setIndexImage1(String indexImage1) {
		this.indexImage1 = indexImage1;
	}

	public String getIndexImage2() {
		return indexImage2;
	}

	public void setIndexImage2(String indexImage2) {
		this.indexImage2 = indexImage2;
	}

	public String getIndexImage3() {
		return indexImage3;
	}

	public void setIndexImage3(String indexImage3) {
		this.indexImage3 = indexImage3;
	}

	public String getIndexImage4() {
		return indexImage4;
	}

	public void setIndexImage4(String indexImage4) {
		this.indexImage4 = indexImage4;
	}

	public String getPostText2() {
		return postText2;
	}

	public void setPostText2(String postText2) {
		this.postText2 = postText2;
	}

	public String getPostText1() {
		return postText1;
	}

	public void setPostText1(String postText1) {
		this.postText1 = postText1;
	}

	public String getPostImage1() {
		return postImage1;
	}

	public void setPostImage1(String postImage1) {
		this.postImage1 = postImage1;
	}

	public String getPostImage2() {
		return postImage2;
	}

	public void setPostImage2(String postImage2) {
		this.postImage2 = postImage2;
	}

	public String getConfigUuid() {
		return configUuid;
	}

	public void setConfigUuid(String configUuid) {
		this.configUuid = configUuid;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public String getAppIntroduce() {
		return appIntroduce;
	}

	public void setAppIntroduce(String appIntroduce) {
		this.appIntroduce = appIntroduce;
	}

}