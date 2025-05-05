package com.weather.demo.weatherapp.transformer;

import com.weather.demo.weatherapp.domain.CityWeather;
import com.weather.demo.weatherapp.entity.OpenWeatherResponseEntity;
import com.weather.demo.weatherapp.entity.WeatherResponse;
import org.springframework.stereotype.Service;

@Service
public class OpenWeatherTransformer {

    public CityWeather transformToDomain(final OpenWeatherResponseEntity entity) {
        return CityWeather.builder()
                .weather(entity.getWeather()[0].getMain())
                .details(entity.getWeather()[0].getDescription())
                .id(String.valueOf(entity.getWeather()[0].getId()))
                .temp(entity.getMain().getTemp())
                .feels_like(entity.getMain().getFeels_like())
                .humidity(entity.getMain().getHumidity())
                .pressure(entity.getMain().getPressure())
                .windSpeed(entity.getWind().getSpeed())
                .windDeg(entity.getWind().getDeg())
                .windGust(entity.getWind().getGust())
                .rain1h(entity.getRain() != null ? entity.getRain().get_1h() : 0.0) // Uso del atributo rain
                .build();
    }

    public WeatherResponse transformToEntity(final CityWeather cityWeather) {
        return WeatherResponse.builder()
                .weather(cityWeather.getWeather())
                .details(cityWeather.getDetails())
                .id(cityWeather.getId())
                .temp(cityWeather.getTemp())
                .feels_like(cityWeather.getFeels_like())
                .humidity(cityWeather.getHumidity())
                .pressure(cityWeather.getPressure())
                .windSpeed(cityWeather.getWindSpeed())
                .windDeg(cityWeather.getWindDeg())
                .windGust(cityWeather.getWindGust())
                .rain1h(cityWeather.getRain1h())
                .build();
    }
}