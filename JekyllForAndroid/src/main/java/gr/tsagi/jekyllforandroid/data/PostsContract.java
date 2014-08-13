package gr.tsagi.jekyllforandroid.data;

import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tsagi on 8/8/14.
 */
public class PostsContract {

    // The "Content authority" is a name for the entire content provider, similar to the
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "gr.tsagi.jekyllforandroid";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://gr.tsagi.jekyllforandroid/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_POSTS = "posts";
    public static final String PATH_TAGS_RELATIONS = "tags_relations";
    public static final String PATH_TAGS = "tags";
    public static final String PATH_CATEGORIES = "categories";

    // Format used for storing dates in the database.  ALso used for converting those strings
    // back into date objects for comparison/processing.
    public static final String DATE_FORMAT = "yyyyMMdd";

    /**
     * Converts Date class to a string representation, used for easy comparison and database lookup.
     * @param date The input date
     * @return a DB-friendly representation of the date, using the format defined in DATE_FORMAT.
     */
    public static String getDbDateString(Date date){
        // Because the API returns a unix timestamp (measured in seconds),
        // it must be converted to milliseconds in order to be converted to valid date.
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * Converts a dateText to a long Unix time representation
     * @param dateText the input date string
     * @return the Date object
     */
    public static Date getDateFromDb(String dateText) {
        SimpleDateFormat dbDateFormat = new SimpleDateFormat(DATE_FORMAT);
        try {
            return dbDateFormat.parse(dateText);
        } catch ( ParseException e ) {
            e.printStackTrace();
            return null;
        }
    }

    /* Inner class that defines the table contents of the location table */
    public static final class PostEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_POSTS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_POSTS;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_POSTS;

        // Table name
        public static final String TABLE_NAME = "posts";

        public static final String COLUMN_DRAFT = "draft";
        public static final String COLUMN_CATEGORY_KEY = "category_id";

        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POST_ID = "id";
        public static final String COLUMN_DATETEXT = "date";
        public static final String COLUMN_CONTENT = "content";


        public static Uri buildPostsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildPosts() {
            return CONTENT_URI.buildUpon().build();
        }

        public static String getDateFromUri(Uri uri) {
            return uri.getQueryParameter(COLUMN_DATETEXT);
        }
    }

    /* Inner class that defines the table contents of the location table */
    public static final class TagsRelationsEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TAGS_RELATIONS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TAGS_RELATIONS;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TAGS_RELATIONS;

        // Table name
        public static final String TABLE_NAME = "tags_relations";

        public static final String COLUMN_POST_TITLE = "post_title";
        public static final String COLUMN_TAG_KEY = "tag_id";

        public static Uri buildTagsRelationsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the table contents of the location table */
    public static final class TagEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TAGS).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_TAGS;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_TAGS;

        // Table name
        public static final String TABLE_NAME = "tags";

        public static final String COLUMN_NAME = "name";

        public static Uri buildTagsUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the table contents of the location table */
    public static final class CategoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_CATEGORIES).build();

        public static final String CONTENT_TYPE =
                "vnd.android.cursor.dir/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORIES;
        public static final String CONTENT_ITEM_TYPE =
                "vnd.android.cursor.item/" + CONTENT_AUTHORITY + "/" + PATH_CATEGORIES;

        // Table name
        public static final String TABLE_NAME = "categories";

        // Columns with the foreign key into the posts & drafts table.
        public static final String COLUMN_POST_KEY = "posts_id";

        public static final String COLUMN_NAME = "name";

        public static Uri buildCategoryUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}
