package si.fri.tpo.team7.entities.curriculum;

import si.fri.tpo.team7.entities.users.Lecturer;

public interface ICourse{
    int getEcts();
    String getName();
    Lecturer getLecturer1();
}
