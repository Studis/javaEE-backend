package si.fri.tpo.team7.entities.users;

import com.neovisionaries.i18n.CountryCode;
import com.sun.istack.internal.NotNull;
import si.fri.tpo.team7.entities.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Residence extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="municipality", nullable=false)
    private Municipality municipality;

    private String country;

    private String placeOfResidence;

    private String postalNumber;

    public void setCountry(@NotNull String country) throws IllegalArgumentException {
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

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public String getCountry() {
        return country;
    }

    public String getPlaceOfResidence() {
        return placeOfResidence;
    }

    public void setPlaceOfResidence(String placeOfResidence) {
        this.placeOfResidence = placeOfResidence;
    }

    public String getPostalNumber() {
        return postalNumber;
    }

    public void setPostalNumber(String postalNumber) {
        this.postalNumber = postalNumber;
    }
}
