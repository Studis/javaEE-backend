package si.fri.tpo.team7.entities.location;

import com.neovisionaries.i18n.CountryCode;
import lombok.Data;
import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.location.Municipality;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Entity
public class Residence extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="municipality")
    private Municipality municipality;

    private String country;

    private String placeOfResidence;

    private String postalNumber;

    //https://stackoverflow.com/questions/139867/is-there-an-open-source-java-enum-of-iso-3166-1-country-codes
    public void setCountry(String country) throws IllegalArgumentException {
        try {
            CountryCode cc = CountryCode.getByCodeIgnoreCase(country);
            if(cc == null) {
                throw new Exception("Country does not exist.");
            }
            this.country = country;
        } catch (Exception e) {
            throw new IllegalArgumentException(country + " is not a valid country.");
        }
    }
}
