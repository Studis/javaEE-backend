package si.fri.tpo.team7.entities.users;

import si.fri.tpo.team7.entities.BaseEntity;
import si.fri.tpo.team7.entities.users.Municipality;

import javax.persistence.*;

@Entity
public class Residence extends BaseEntity {

    @ManyToOne
    @JoinColumn(name="municipality", nullable=false)
    private Municipality municipality;

    private String country;

    private String placeOfResidence;

    private String postalNumber;

    public Municipality getMunicipality() {
        return municipality;
    }

    public void setMunicipality(Municipality municipality) {
        this.municipality = municipality;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
