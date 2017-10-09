package com.aspectsense.gamechanger;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aspectsense.gamechanger.model.Resource;
import com.aspectsense.gamechanger.model.Story;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Vector;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.aspectsense.gamechanger.ActivityLoadResources.BUFFER_SIZE;

/**
 * @author Nearchos
 *         Created: 30-Nov-16
 */

public class StoriesAdapter extends ArrayAdapter<Story> {

    private LayoutInflater inflater;

    public StoriesAdapter(final Context context, final Vector<Story> stories) {
        super(context, R.layout.story_list_item, stories);
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = inflater.inflate(R.layout.story_list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        final Story story = getItem(position);
        assert story != null;

        // initially set the image to 'downloading ...' until it is replaced
        viewHolder.storyImageView.setImageResource(R.drawable.downloading);

        final String thumbnailResourceId = story.getThumbnail();
        final Resource thumbnailResource = story.getResourceById(thumbnailResourceId);

        if(Utils.hasResourceInCache(getContext(), story.getId(), thumbnailResource)) {
            final byte [] data = Utils.getResourceFromCache(getContext(), story.getId(), thumbnailResource);
            assert data != null;
            final Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            viewHolder.storyImageView.setImageBitmap(bitmap);
        } else {
            new DownloadImageTask(thumbnailResource, viewHolder.storyImageView).execute(story.getId(), thumbnailResource.getSource());
        }

        viewHolder.storyNameTextView.setText(story.getTitle());
        final String description = story.getDescription();
        viewHolder.storyDescriptionTextView.setText(description);
        return convertView;
    }

    public static class ViewHolder {
        @BindView(R.id.story_list_item_image) ImageView storyImageView;
        @BindView(R.id.story_list_item_name) TextView storyNameTextView;
        @BindView(R.id.story_list_item_description) TextView storyDescriptionTextView;

        ViewHolder(final View view) {
            ButterKnife.bind(this, view);
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        private Resource resource;
        private ImageView imageView;

        DownloadImageTask(final Resource resource, final ImageView imageView) {
            this.resource = resource;
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String... params) {
            String storyId = params[0];
            String url = params[1];
            Bitmap bitmap = null;
            try {
                {
                    final InputStream inputStream = new java.net.URL(url).openStream();
                    final byte[] data = new byte[BUFFER_SIZE];
                    final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
                    int nRead;
                    while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
                        buffer.write(data, 0, nRead);
                    }
                    buffer.flush();
                    Utils.saveResourceInCache(getContext(), storyId, resource, buffer.toByteArray());
                }
                {
                    final byte[] data = Utils.getResourceFromCache(getContext(), storyId, resource);
                    assert data != null;
                    bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                }
            } catch (IOException ioe) {
                Log.e("Error", "I/O error while downloading and caching resource: " + ioe.getMessage());
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap result) {
            imageView.setImageBitmap(result);
        }
    }
}