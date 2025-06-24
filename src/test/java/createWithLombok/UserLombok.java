package createWithLombok;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserLombok {

	
	private String name;
	private String email;
	private String gender;
	private String status;
	
}
