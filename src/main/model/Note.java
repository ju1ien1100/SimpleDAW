package model;

//Note this is a data abstraction for ease of design
public class Note {
    private String pitch;
    private long duration;
    private int velocity;


    //EFFECTS: constructs a Note with a string representation of a pitch
    public Note(String pitch, long duration, int velocity) {
        this.pitch = pitch;
        this.duration  = duration;
        this.velocity = velocity;
    }

    //EFFECTS: These are the getters

    public String getPitch() {
        return pitch;
    }

    public long getDuration() {
        return duration;
    }

    public int getVelocity() {
        return velocity;
    }

    //EFFECTS: These are the setters
    //MODIFIES: this

    public void setPitch(String pitch) {
        this.pitch = pitch;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }
}
