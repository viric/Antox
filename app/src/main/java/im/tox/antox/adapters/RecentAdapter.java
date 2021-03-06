package im.tox.antox.adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.support.v4.widget.ResourceCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.sql.Timestamp;

import im.tox.antox.R;
import im.tox.antox.tox.ToxSingleton;
import im.tox.antox.utils.IconColor;
import im.tox.antox.utils.PrettyTimestamp;
import im.tox.antox.utils.UserStatus;

public class RecentAdapter extends ResourceCursorAdapter {
	Context context;
    int layoutResourceId = R.layout.contact_list_item;
    private LayoutInflater mInflater;
    private ToxSingleton toxSingleton = ToxSingleton.getInstance();

	public RecentAdapter(Context context, Cursor c) {
        super(context, R.layout.contact_list_item, c, 0);
		this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return mInflater.inflate(this.layoutResourceId, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        final String tox_key = cursor.getString(0);
        final String username = cursor.getString(1);
        final boolean online = cursor.getInt(2) !=0;
        final String status = cursor.getString(3);
        final Timestamp time = Timestamp.valueOf(cursor.getString(4));
        final String message = cursor.getString(5);
        final int unreadCount = cursor.getInt(6);
        FriendsListHolder holder = new FriendsListHolder();
        holder.icon = (TextView) view.findViewById(R.id.icon);
        holder.friendName = (TextView) view.findViewById(R.id.friend_name);
        holder.friendStatus = (TextView) view
                .findViewById(R.id.friend_status);
        holder.timestamp = (TextView) view.findViewById(R.id.last_message_timestamp);
        holder.unreadCount = (TextView) view.findViewById(R.id.unread_messages_count);

        holder.friendName.setText(username);
        holder.friendStatus.setText(message);
        holder.unreadCount.setText(Integer.toString(unreadCount));
        holder.timestamp.setText(PrettyTimestamp.prettyTimestamp(time, false));
        holder.icon.setBackgroundColor(IconColor.iconColorAsColor(online, UserStatus.getToxUserStatusFromString(status)));
        if (unreadCount == 0) {
            holder.unreadCount.setVisibility(View.GONE);
        } else {
            holder.unreadCount.setVisibility(View.VISIBLE);
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setSelected(true);
                toxSingleton.changeActiveKey(tox_key);
            }
        });
    }

	static class FriendsListHolder {
		TextView icon;
		TextView friendName;
		TextView friendStatus;
        TextView timestamp;
        TextView unreadCount;
	}

}
