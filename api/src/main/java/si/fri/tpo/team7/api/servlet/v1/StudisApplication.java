package si.fri.tpo.team7.api.servlet.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/v1")
public class StudisApplication extends Application {
    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> resources = new HashSet<>();
        return resources;
    }
}

