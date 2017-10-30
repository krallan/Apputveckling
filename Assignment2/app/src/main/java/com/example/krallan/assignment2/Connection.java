package com.example.krallan.assignment2;

import android.util.JsonWriter;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Connection implements Runnable {

    public static final String SERVER_IP = "195.178.227.53";
    public static final int SERVER_PORT = 7117;
    private Buffer buffer;
    private Socket socket;
    private Reader reader;
    private Writer writer;
    private Thread readerThread;
    private Thread writerThread;
    private MainActivity mainActivity;
    private boolean isJoined = false;
    private String id;
    private String group;
    private Thread locationUpdater;

    public Connection(MainActivity mainActivity) {
        buffer = new Buffer();
        this.mainActivity = mainActivity;
    }

    public boolean isJoined() {
        return isJoined;
    }

    public void readMessage(String message) {
        try {
            JSONObject jsonObject = new JSONObject(message);
            String messageType = jsonObject.getString("type");
            if (messageType.equals("register")) {
                group = jsonObject.getString("group");
                id = jsonObject.getString("id");
                isJoined = true;
                mainActivity.updateCurrentGroup(group);
                locationUpdater = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while(isJoined && Thread.interrupted()==false){
                            StringWriter stringWriter = new StringWriter();
                            JsonWriter jsonWriter = new JsonWriter(stringWriter);
                            try {
                                LatLng myLocation = mainActivity.getMyLocation();
                                String latitude = String.valueOf(myLocation.latitude);
                                String longitude = String.valueOf(myLocation.longitude);
                                jsonWriter.beginObject()
                                        .name("type").value("location")
                                        .name("id").value(id)
                                        .name("latitude").value(latitude)
                                        .name("longitude").value(longitude)
                                        .endObject();
                                buffer.put(stringWriter.toString());
                                Thread.sleep(30000);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
                locationUpdater.start();
            } else if (messageType.equals("unregister")) {
                isJoined = false;
                locationUpdater.join();
                locationUpdater = null;
                mainActivity.updateCurrentGroup("");
            } else if (messageType.equals("members")) {
                String group = jsonObject.getString("group");
                JSONArray jsonMembers = jsonObject.getJSONArray("members");
                List<String> members = new ArrayList<>();
                for (int i = 0; i < jsonMembers.length(); i++) {
                    JSONObject jsonMember = jsonMembers.getJSONObject(i);
                    String member = jsonMember.getString("member");
                    members.add(member);
                }
            } else if (messageType.equals("groups")) {
                JSONArray jsonGroups = jsonObject.getJSONArray("groups");
                List<String> groups = new ArrayList<>();
                for (int i = 0; i < jsonGroups.length(); i++) {
                    JSONObject jsonGroup = jsonGroups.getJSONObject(i);
                    String group = jsonGroup.getString("group");
                    groups.add(group);
                }
                JoinGroup joinGroupDialog = mainActivity.getJoinGroupDialog();
                if(joinGroupDialog !=null){
                    joinGroupDialog.updateGroups(groups);
                }
            } else if (messageType.equals("locations")) {
                JSONArray jsonLocations = jsonObject.getJSONArray("location");
                List<Member> members = new ArrayList<>();
                for (int i = 0; i < jsonLocations.length(); i++) {
                    JSONObject jsonlocation = jsonLocations.getJSONObject(i);
                    String member = jsonlocation.getString("member");
                    double latitude = jsonlocation.getDouble("latitude");
                    double longitude = jsonlocation.getDouble("longitude");
                    members.add(
                            new Member(member, longitude, latitude)
                    );
                }
                mainActivity.updateMarkers(members);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void disconnect(){
        if(isJoined){
            StringWriter stringWriter = new StringWriter();
            JsonWriter jsonWriter = new JsonWriter(stringWriter);
            try {
                jsonWriter.beginObject()
                        .name("type").value("unregister")
                        .name("id").value(id)
                        .endObject();
                buffer.put(stringWriter.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void getGroups() {
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(stringWriter);
        try {
            jsonWriter.beginObject()
                    .name("type").value("groups")
                    .endObject();
            buffer.put(stringWriter.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void joinGroup(String group, String member){
        StringWriter stringWriter = new StringWriter();
        JsonWriter jsonWriter = new JsonWriter(stringWriter);
        try {
            jsonWriter.beginObject()
                    .name("type").value("register")
                    .name("group").value(group)
                    .name("member").value(member)
                    .endObject();
            buffer.put(stringWriter.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println("ConnectionThread started");
            socket = new Socket(SERVER_IP, SERVER_PORT);
            reader = new Reader(socket, this);
            writer = new Writer(socket, buffer);
            readerThread = new Thread(reader);
            writerThread = new Thread(writer);
            readerThread.start();
            writerThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
