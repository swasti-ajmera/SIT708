package com.example.newsapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.fragment.app.FragmentTransaction;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView topStoriesRecyclerView, newsRecyclerView;
    List<NewsItem> topStoriesList, newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        topStoriesRecyclerView = findViewById(R.id.topStoriesRecyclerView);
        newsRecyclerView = findViewById(R.id.newsRecyclerView);

        // Create sample data
        topStoriesList = new ArrayList<>();
        newsList = new ArrayList<>();

        topStoriesList.add(new NewsItem("Spiderman kicks a civilian", "“Hero or Menace?” Debate Ignites Across New York\n" +
                "\n" +
                "New York City was left stunned this afternoon when footage surfaced showing the beloved neighborhood vigilante, Spider-Man, delivering a swift kick to an unsuspecting civilian in Times Square. Witnesses gasped as the masked hero, known for his acrobatics and selfless crime-fighting, launched into what appeared to be an unprovoked attack.\n" +
                "\n" +
                "The incident, caught on a bystander's phone, shows the web-slinger swinging down from a building, landing with precision—only to immediately lash out with a forceful kick that sent the civilian tumbling to the ground.\n" +
                "\n" +
                "“He just flew in and BAM! Kicked the guy like it was Mortal Kombat,” said one witness, still in shock. “I thought Spider-Man was supposed to save people, not dropkick them!”\n" +
                "\n" +
                "Authorities have launched an investigation, and public opinion is sharply divided. While some insist there must be more to the story, others are calling for accountability, demanding answers from the masked crusader.\n" +
                "\n" +
                "Mayor Jameson has already issued a statement: “This is what happens when we let masked vigilantes operate without oversight. The city deserves heroes, not hazards.”\n" +
                "\n" +
                "Is Spider-Man losing control—or is there something more sinister at play?\n" +
                "\n" +
                "Stay tuned as this story develops.", R.drawable.sample1));
        topStoriesList.add(new NewsItem("Last V8 car on a high-speed run", "\"One Man, One Car, One Last Roar Against the Silence\"\n" +
                "\n" +
                "In a scene straight out of an action blockbuster, the last known Shelby Cobra—a throaty, tire-screeching relic of pure American horsepower—tore through the Nevada desert today in a desperate, defiant sprint against the tide of history.\n" +
                "\n" +
                "With the Global Electric Transition Act set to take effect at midnight, all combustion engine vehicles are slated to be permanently decommissioned. But one driver, identity unknown, had other plans.\n" +
                "\n" +
                "“The ground shook,” said a stunned onlooker. “That thing came roaring out of the sunrise like a metallic beast... it didn’t sound like a car, it sounded like a war cry.”\n" +
                "\n" +
                "Authorities say the Cobra broke through three eco-checkpoints, outran four electric pursuit drones, and left behind nothing but the smell of burning rubber and gasoline-laced rebellion.\n" +
                "\n" +
                "Environmental watchdogs call the stunt “reckless,” while underground car culture forums are already hailing the driver as a legend. Social media is ablaze with the hashtag: #LastRoar.\n" +
                "\n" +
                "World leaders are reportedly furious, fearing the stunt could spark a “combustion revolution,” undermining decades of progress toward a zero-emissions future.\n" +
                "\n" +
                "As night falls, the Cobra remains at large—its V8 heart thundering into legend.\n" +
                "\n" +
                "Is this the final breath of the old world, or the beginning of a motorized resistance?", R.drawable.sample2));
        topStoriesList.add(new NewsItem("Spiderman snatches the camera from a paparazzi", "“The Masked Menace Strikes Again!” cries Daily Bulletin\n" +
                "\n" +
                "Chaos erupted in downtown Manhattan this afternoon when Spider-Man, the city’s famously elusive wall-crawler, lashed out at a local paparazzo—snatching his high-end camera right out of his hands and vanishing into the skyline.\n" +
                "\n" +
                "The photographer, Miles Denton, known for chasing celebrity sightings and elusive vigilantes, claims he was simply doing his job when the web-slinger dropped from a building and tore the camera away in what he called “an aggressive, almost threatening move.”\n" +
                "\n" +
                "“He didn’t even say a word,” Denton recounted, still shaken. “He just grabbed it and zipped off like he owned the place. That camera cost me twelve grand! And what’s he hiding, huh?”\n" +
                "\n" +
                "Witnesses described the incident as sudden and surreal, with some even cheering, tired of paparazzi invading privacy. Others, however, question the hero’s increasingly erratic behavior.\n" +
                "\n" +
                "“This is the third outburst in two weeks,” said NYPD spokesperson Gina Morales. “First the alleged civilian kick, now this? The question isn’t ‘Who is Spider-Man?’ anymore—it’s ‘Can we still trust him?’”\n" +
                "\n" +
                "Rumors swirl that the camera contained rare, close-up footage of Spider-Man unmasking—footage now conveniently gone.\n" +
                "\n" +
                "As tensions rise between the press and the powered, one thing’s for sure: the line between hero and vigilante is getting blurrier by the day.", R.drawable.sample3));
        topStoriesList.add(new NewsItem("Top Story 4", "Description of Top Story 4", R.drawable.sample4));

        newsList.add(new NewsItem("News 1", "Description of News 1", R.drawable.sample5));
        newsList.add(new NewsItem("News 2", "Description of News 2", R.drawable.sample6));
        newsList.add(new NewsItem("News 3", "Description of News 3", R.drawable.sample7));
        newsList.add(new NewsItem("News 4", "Description of News 4", R.drawable.sample7));
        newsList.add(new NewsItem("News 4", "Description of News 4", R.drawable.sample7));


        topStoriesRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        newsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // 2 items per row in News section

        topStoriesRecyclerView.setAdapter(new NewsAdapter(topStoriesList));
        newsRecyclerView.setAdapter(new NewsAdapter(newsList));

        // Optional: Add spacing between grid items
        newsRecyclerView.addItemDecoration(new SpaceItemDecoration(16));
    }
}
