package com.augustbonds.keyboardpiano;

import javax.sound.midi.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import com.augustbonds.foundation.Dictionary;

public class Main {

    private static final Dictionary<Character, Notes> KEYS_TO_NOTES = new Dictionary<>();
    private static Synthesizer midiSynth;
    private static Instrument[] instr;
    private static MidiChannel[] mChannels;

    private static Set<Notes> beingPlayed = new HashSet<>();

    public static void main(String[] args) {
        populateKeys();
        loadSynth();

        JFrame f = new JFrame();

        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        f.setSize(100,100);
        f.setVisible(true);

        f.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
//                playChord(KEYS_TO_NOTES.get(e.getKeyChar()));
                //Do nothing
            }

            @Override
            public void keyPressed(KeyEvent e) {
                Notes note = KEYS_TO_NOTES.get(e.getKeyChar());
                if (!beingPlayed.contains(note)) {
                    startNote(note);
                    beingPlayed.add(note);
                }
                Logger.info("Key pressed: " + e.getKeyChar());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                Logger.info("Key released: " + e.getKeyChar());
                Notes note = KEYS_TO_NOTES.get(e.getKeyChar());

                stopNote(note);
                beingPlayed.remove(note);
            }
        });
    }

    private static void loadSynth(){
        try {
            midiSynth = MidiSystem.getSynthesizer();
            midiSynth.open();

            instr = midiSynth.getDefaultSoundbank().getInstruments();
            mChannels = midiSynth.getChannels();

            midiSynth.loadInstrument(instr[0]);

        } catch (MidiUnavailableException e) {
            throw new RuntimeException("Failed to load midi");
        }
    }

    private static void playNote(Notes note){
        playNote(note.getMidi());
    }

    private static void playChord(Notes note){
        playChord(note.getMidi());
    }

    static synchronized void startNote(Notes note){
        mChannels[0].noteOn(note.getMidi(), 100);
    }

    static synchronized void stopNote(Notes note){
        mChannels[0].noteOff(note.getMidi());
    }

    private static void playNote(int note){
        mChannels[0].noteOn(note, 100); //On channel 0, play note number 60 with velocity 100
        try { Thread.sleep(300); // wait time in milliseconds to control duration
        } catch( InterruptedException e ) { }
        mChannels[0].noteOff(note);//turn of the note
    }

    private static void playChord(int note){
        mChannels[0].noteOn(note, 100); //On channel 0, play note number 60 with velocity 100
        mChannels[0].noteOn(note+4, 100); //On channel 0, play note number 60 with velocity 100
        mChannels[0].noteOn(note+7, 100); //On channel 0, play note number 60 with velocity 100
        try { Thread.sleep(300); // wait time in milliseconds to control duration
        } catch( InterruptedException e ) { }
        mChannels[0].noteOff(note);//turn of the note
        mChannels[0].noteOff(note+4); //On channel 0, play note number 60 with velocity 100
        mChannels[0].noteOff(note+7); //On channel 0, play note number 60 with velocity 100
    }



    private enum Notes {

        Diss(51),
        E(52),
        F(53),
        Fiss(54),
        G(55),
        Giss(56),
        A(57),
        B(58),
        H(59),
        c(60),
        ciss(61),
        d(62),
        diss(63),
        e(64),
        f(65),
        fiss(66),
        g(67),
        giss(68),
        a(69),
        b(70),
        h(71),
        c1(72),
        ciss1(73),
        d1(74);

        private final int midi;

        Notes(int midi) {
            this.midi = midi;
        }

        public int getMidi() {
            return midi;
        }
    }

    private static void populateKeys(){
        KEYS_TO_NOTES.put('q', Notes.Diss);
        KEYS_TO_NOTES.put('Q', Notes.Diss);
        KEYS_TO_NOTES.put('a', Notes.E);
        KEYS_TO_NOTES.put('A', Notes.E);
        KEYS_TO_NOTES.put('s', Notes.F);
        KEYS_TO_NOTES.put('S', Notes.F);
        KEYS_TO_NOTES.put('e', Notes.Fiss);
        KEYS_TO_NOTES.put('E', Notes.Fiss);
        KEYS_TO_NOTES.put('d', Notes.G);
        KEYS_TO_NOTES.put('D', Notes.G);
        KEYS_TO_NOTES.put('r', Notes.Giss);
        KEYS_TO_NOTES.put('R', Notes.Giss);
        KEYS_TO_NOTES.put('f', Notes.A);
        KEYS_TO_NOTES.put('F', Notes.A);
        KEYS_TO_NOTES.put('t', Notes.B);
        KEYS_TO_NOTES.put('T', Notes.B);
        KEYS_TO_NOTES.put('g', Notes.H);
        KEYS_TO_NOTES.put('G', Notes.H);
        KEYS_TO_NOTES.put('h', Notes.c);
        KEYS_TO_NOTES.put('H', Notes.c);
        KEYS_TO_NOTES.put('u', Notes.ciss);
        KEYS_TO_NOTES.put('U', Notes.ciss);
        KEYS_TO_NOTES.put('j', Notes.d);
        KEYS_TO_NOTES.put('J', Notes.d);
        KEYS_TO_NOTES.put('i', Notes.diss);
        KEYS_TO_NOTES.put('I', Notes.diss);
        KEYS_TO_NOTES.put('k', Notes.e);
        KEYS_TO_NOTES.put('K', Notes.e);
        KEYS_TO_NOTES.put('l', Notes.f);
        KEYS_TO_NOTES.put('L', Notes.f);
        KEYS_TO_NOTES.put('p', Notes.fiss);
        KEYS_TO_NOTES.put('P', Notes.fiss);
        KEYS_TO_NOTES.put(';', Notes.g);
        KEYS_TO_NOTES.put(':', Notes.g);
        KEYS_TO_NOTES.put('[', Notes.giss);
        KEYS_TO_NOTES.put('{', Notes.giss);
        KEYS_TO_NOTES.put('\'', Notes.a);
        KEYS_TO_NOTES.put('\"', Notes.a);
        KEYS_TO_NOTES.put(']', Notes.b);
        KEYS_TO_NOTES.put('}', Notes.b);
        KEYS_TO_NOTES.put('\\', Notes.h);
        KEYS_TO_NOTES.put('|', Notes.h);
    }
}
