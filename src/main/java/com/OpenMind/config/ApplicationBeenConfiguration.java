package com.OpenMind.config;

import com.OpenMind.models.entitis.ProfessionalField;
import com.OpenMind.models.enums.FieldName;
import com.OpenMind.models.enums.MeetingType;
import com.OpenMind.serveces.ArticleService;
import com.cloudinary.Cloudinary;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Map;

@Configuration
public class ApplicationBeenConfiguration {

    private final CloudinaryConfig cloudinaryConfig;

    public ApplicationBeenConfiguration(CloudinaryConfig cloudinaryConfig) {
        this.cloudinaryConfig = cloudinaryConfig;
    }


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
//        modelMapper.addConverter(new Converter<String, LocalDateTime>() {
//            @Override
//            public LocalDateTime convert(MappingContext<String, LocalDateTime> mappingContext) {
//                return LocalDateTime
//                        .parse(mappingContext.getSource(),
//                                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
//            }
//        });
        modelMapper.addConverter(new Converter<LocalDateTime, String>() {
            @Override
            public String convert(MappingContext<LocalDateTime, String> mappingContext) {
                return String.format("%s",(mappingContext.getSource())).replaceAll("T", " ");
            }
        });

        modelMapper.addConverter(new Converter<MeetingType, String>() {
            @Override
            public String convert(MappingContext<MeetingType, String> mappingContext) {

                return String.format("%s",(mappingContext.getSource().name())).charAt(0) +
                 String.format("%s",(mappingContext.getSource().name())).substring(1).toLowerCase()
                         .replaceAll("_", " ");
            }
        });

        modelMapper.addConverter(new Converter<ProfessionalField, String>() {
            @Override
            public String convert(MappingContext<ProfessionalField, String> mappingContext) {

                return String.format("%s",(mappingContext.getSource().getFieldName().name())).charAt(0) +
                        String.format("%s",(mappingContext.getSource().getFieldName().name())).substring(1).toLowerCase()
                                .replaceAll("_", " ");
            }
        });

        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder();

    }

    @Bean
    public OpenMindMethodSecurityExpressionHandler createExpressionHandler(ArticleService articleService){
        return new OpenMindMethodSecurityExpressionHandler(articleService);
    }

    @Bean
    public Cloudinary cloudinary(){

        return new Cloudinary(
                Map.of(
                        "cloud_name", cloudinaryConfig.getCloudName(),
                        "api_key", cloudinaryConfig.getApiKey(),
                        "api_secret", cloudinaryConfig.getApiSecret()
                )
        );
    }
}
