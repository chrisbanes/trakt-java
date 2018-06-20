package com.uwetrottmann.trakt5;

import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Moshi.Builder;
import com.uwetrottmann.trakt5.enums.ListPrivacy;
import com.uwetrottmann.trakt5.enums.Rating;
import com.uwetrottmann.trakt5.enums.Status;
import java.io.IOException;
import javax.annotation.Nullable;
import org.threeten.bp.LocalDate;
import org.threeten.bp.OffsetDateTime;

public class TraktV2Helper {
    public static Moshi.Builder getMoshiBuilder() {
        Moshi.Builder builder = new Builder();

        // trakt exclusively uses ISO 8601 date times with milliseconds and time zone offset
        // such as '2011-12-03T10:15:30.000+01:00' or '2011-12-03T10:15:30.000Z'
        builder.add(OffsetDateTime.class, new JsonAdapter<OffsetDateTime>() {
            @Nullable
            @Override
            public OffsetDateTime fromJson(JsonReader jsonReader) throws IOException {
                return OffsetDateTime.parse(jsonReader.nextString());
            }

            @Override
            public void toJson(JsonWriter jsonWriter, @Nullable OffsetDateTime o) throws IOException {
                jsonWriter.value(o.toString());

            }
        });

        // dates are in ISO 8601 format as well
        builder.add(LocalDate.class, new JsonAdapter<LocalDate>() {
            @Nullable
            @Override
            public LocalDate fromJson(JsonReader jsonReader) throws IOException {
                return LocalDate.parse(jsonReader.nextString());
            }

            @Override
            public void toJson(JsonWriter jsonWriter, @Nullable LocalDate o) throws IOException {
                jsonWriter.value(o.toString());
            }
        });


        // list privacy
        builder.add(ListPrivacy.class, new JsonAdapter<ListPrivacy>() {
            @Nullable
            @Override
            public ListPrivacy fromJson(JsonReader jsonReader) throws IOException {
                return ListPrivacy.fromValue(jsonReader.nextString());
            }

            @Override
            public void toJson(JsonWriter jsonWriter, @Nullable ListPrivacy o) throws IOException {
                jsonWriter.value(o.toString());
            }
        });

        // rating
        builder.add(Rating.class, new JsonAdapter<Rating>() {
            @Nullable
            @Override
            public Rating fromJson(JsonReader jsonReader) throws IOException {
                return Rating.fromValue(jsonReader.nextInt());
            }

            @Override
            public void toJson(JsonWriter jsonWriter, @Nullable Rating o) throws IOException {
                jsonWriter.value(o.value);
            }
        });

        // status
        builder.add(Status.class, new JsonAdapter<Status>() {
            @Nullable
            @Override
            public Status fromJson(JsonReader jsonReader) throws IOException {
                return Status.fromValue(jsonReader.nextString());
            }

            @Override
            public void toJson(JsonWriter jsonWriter, @Nullable Status o) throws IOException {
                jsonWriter.value(o.toString());
            }
        });
        return builder;
    }
}
