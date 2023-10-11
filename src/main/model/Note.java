package model;


public class Note {
    private String pitch;
    private long duration;
    private int velocity;


    //EFFECTS: constructs an empty list of notes
    public Note(String pitch, long duration, int velocity) {
        this.pitch = pitch;
        this.duration  = duration;
        this.velocity = velocity;
    }

    public void playNote() {
        return;
    }

    public String getPitch() {
        return pitch;
    }

    public long getDuration() {
        return duration;
    }

    public int getVelocity() {
        return velocity;
    }

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
