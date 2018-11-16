package com.dhiraj.GeekTrust.service;

import com.dhiraj.GeekTrust.util.DayNightType;
import com.dhiraj.GeekTrust.util.Teams;
import com.dhiraj.GeekTrust.util.TossType;
import com.dhiraj.GeekTrust.util.WeatherType;
import javafx.util.Pair;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Toss {

    private Integer evaluateToss(Teams teams, String [] inputs){

        Integer type = 0;
        for(String input: inputs){
            WeatherType[] weatherTypes = WeatherType.values();
            for(WeatherType weatherType : weatherTypes){
                if(weatherType.toString().equalsIgnoreCase(input)){
                    if(tossWeatherMatix.containsKey(new AbstractMap.SimpleEntry<>(teams, weatherType)))
                        type = tossWeatherMatix.get(new AbstractMap.SimpleEntry<>(teams, weatherType)).ordinal();
                    break;

                }
            }

            DayNightType[] dayNightTypes = DayNightType.values();
            for(DayNightType dayNightType : dayNightTypes){
                if(dayNightType.toString().equalsIgnoreCase(input)){
                    if(tossDayMatrix.containsKey(new AbstractMap.SimpleEntry<>(teams, dayNightType)))
                        type |= tossDayMatrix.get(new AbstractMap.SimpleEntry<>(teams, dayNightType)).ordinal();
                    break;
                }
            }
        }
        return type;
    }

    private Map<AbstractMap.SimpleEntry<Teams,WeatherType>, TossType> tossWeatherMatix;
    private Map<AbstractMap.SimpleEntry<Teams, DayNightType>,TossType> tossDayMatrix;

    public Toss(){
        buildMap();
    }

    private  void buildMap(){
        tossWeatherMatix = new HashMap<AbstractMap.SimpleEntry<Teams, WeatherType>, TossType>();
        tossDayMatrix = new HashMap<AbstractMap.SimpleEntry<Teams, DayNightType>, TossType>();
        tossWeatherMatix.put(new AbstractMap.SimpleEntry<>(Teams.Lengaburu, WeatherType.CLEAR),TossType.BATS);
        tossWeatherMatix.put(new AbstractMap.SimpleEntry<>(Teams.Lengaburu, WeatherType.CLOUDY),TossType.BOWLS);
        tossWeatherMatix.put(new AbstractMap.SimpleEntry<>(Teams.Enchai, WeatherType.CLEAR),TossType.BOWLS);
        tossWeatherMatix.put(new AbstractMap.SimpleEntry<>(Teams.Enchai, WeatherType.CLOUDY),TossType.BATS);
        tossDayMatrix.put(new AbstractMap.SimpleEntry<>(Teams.Lengaburu, DayNightType.DAY),TossType.BATS);
        tossDayMatrix.put(new AbstractMap.SimpleEntry<>(Teams.Lengaburu, DayNightType.NIGHT),TossType.BOWLS);
        tossDayMatrix.put(new AbstractMap.SimpleEntry<>(Teams.Enchai, DayNightType.DAY),TossType.BOWLS);
        tossDayMatrix.put(new AbstractMap.SimpleEntry<>(Teams.Enchai, DayNightType.NIGHT),TossType.BATS);
    }

    Pair<Teams,Teams>  getTossResult(Pair<Teams, Teams> teams, String ... inputs){
        Random r = new Random(System.currentTimeMillis());
        Teams team1, team2;
        if(r.nextFloat() < 0.5f){
            //Winner
            team1 = teams.getKey();
            //looser
            team2 = teams.getValue();
        }
        else{
            //Winner
            team1 = teams.getValue();
            //looser
            team2 = teams.getKey();
        }
        int result = evaluateToss(team1, inputs);
        if(result>0){
            return new Pair<Teams,Teams>(team1, team1);
        }
        else
            return new Pair<Teams,Teams>(team1, team2);

    }
    /*
    int getTossResult(Teams teams, String ... inputs){
        return evaluateToss(teams, inputs);
    }

*/



}
