package LombokBuilder_POJO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class fakeUserLombok {

	private String username;
	private String email;
	private String password;
	private Name name;
	private Address address;

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Name {
		private String firstname;
		private String lastname;

	}

	@Builder
	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Address {

		private String city;
		private String pincode;
		private Geolocation geolocation;

		@Builder
		@Data
		@AllArgsConstructor
		@NoArgsConstructor
		public static class Geolocation {
			private String lat;
			@JsonProperty("long")
			private String longitutde;
		}
	}

}
