package si.fri.tpo.team7.api.servlet.seeding;

import java.io.PrintWriter;

public abstract class Seeder {
    String content;
    public Seeder(String content){
        this.content = content;
    }

    public void Seed(PrintWriter writer){
        writer.print("Seeding "+content+" ...");

        SeedContent();

        writer.println("Done");
    }

    protected abstract void SeedContent();
}
