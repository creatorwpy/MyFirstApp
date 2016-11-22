package library.mlibrary.util.multiimagepick;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import library.mlibrary.R;
import library.mlibrary.base.AbsBaseFragment;
import library.mlibrary.util.common.CommonUtil;
import library.mlibrary.view.recyclerview.AbsBaseDataAdapter;
import library.mlibrary.view.recyclerview.RecyclerView;

/**
 * Created by harmy on 2016/10/21 0021.
 */

public class MultiImagePickFragment extends AbsBaseFragment implements LoaderManager.LoaderCallbacks<Cursor> {
    @Override
    protected void onSetContentView() {
        setContentView(R.layout.fragment_multiimagepick);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onSetListener() {
        rv_photos.addOnScrollListener(new android.support.v7.widget.RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(android.support.v7.widget.RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                // 当不滚动时
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    tv_time.setVisibility(View.INVISIBLE);
                } else {
                    tv_time.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onScrolled(android.support.v7.widget.RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (tv_time.getVisibility() == View.VISIBLE) {
                    int position = rv_photos.getFirstVisiblePosition();
                    Image image = mAllImages.get(position);
                    tv_time.setText(CommonUtil.formatDate(image.getTime(), "yyyy-MM-dd"));
                }
            }
        });
    }

    @Override
    protected void afterOnCreate(Bundle savedInstanceState) {
        initPhotoList();
        tv_time.setVisibility(View.INVISIBLE);
        getActivity().getSupportLoaderManager().initLoader(LOAD_ALL, null, this);
    }

    private FolderAdapter mFAdapter;
    private PhotoAdapter mPAdapter;

    private ArrayList<Image> mShowingImages;

    private void initPhotoList() {
        mFolders = new ArrayList<>();
        mAllImages = new ArrayList<>();
        mShowingImages = new ArrayList<>();
        mSelectedMaps = new HashMap<>();
//        mFAdapter = new FolderAdapter(getActivity(), mFolders);
        mPAdapter = new PhotoAdapter(getActivity(), mShowingImages);
        rv_photos.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        rv_photos.setAdapter(mPAdapter);
    }

    @Override
    protected void onFindView(View view) {
        super.onFindView(view);
        rv_photos = (RecyclerView) view.findViewById(R.id.rv_photos);
        tv_time = (TextView) view.findViewById(R.id.tv_time);
    }

    //    @Inject(R.id.rv_photos)
    private RecyclerView rv_photos;

    private TextView tv_time;

    private final static int LOAD_ALL = 0;
    private final static int LOAD_CATEGORY = 1;//未使用

    private final String[] IMAGE_PROJECTION = {
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media._ID};

    // 文件夹数据
    private ArrayList<Folder> mFolders;

    private ArrayList<Image> mAllImages;

    private HashMap<Integer, Image> mSelectedMaps;


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        if (listener != null) {
            listener.onStartLoad();
        }
        if (id == LOAD_ALL) {
            CursorLoader cursorLoader = new CursorLoader(getActivity(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    null, null, IMAGE_PROJECTION[2] + " DESC");
            return cursorLoader;
        } else if (id == LOAD_CATEGORY) {
            CursorLoader cursorLoader = new CursorLoader(getActivity(),
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_PROJECTION,
                    IMAGE_PROJECTION[0] + " like '%" + args.getString("path") + "%'", null, IMAGE_PROJECTION[2] + " DESC");
            return cursorLoader;
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null) {
            int count = data.getCount();
            if (count > 0) {
                mAllImages.clear();
                data.moveToFirst();
                do {
                    String path = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[0]));
                    File imageFile = new File(path);
                    if (!imageFile.exists()) {
                        continue;
                    }
                    String name = data.getString(data.getColumnIndexOrThrow(IMAGE_PROJECTION[1]));
                    long dateTime = data.getLong(data.getColumnIndexOrThrow(IMAGE_PROJECTION[2]));
                    Image image = new Image(path, name, dateTime * 1000);
                    mAllImages.add(image);

                    if (!CommonUtil.isEmpty(mDefaults)) {
                        for (int i = 0; i < mDefaults.size(); i++) {
                            if (mDefaults.get(i).equals(image.getPath())) {
                                image.setSelected(true);
                                mSelectedMaps.put(i, image);
                            }
                        }
                    }

                    File folderFile = imageFile.getParentFile();
                    //文件夹
                    //所有图片
                    if (mFolders.size() == 0) {
                        Folder folder = new Folder();
                        folder.setName("所有图片");
                        folder.setPath("所有图片");
                        folder.setCover(image);
                        folder.setIsselected(true);
                        folder.setCount(1);
                        mFolders.add(folder);
                    } else {
                        Folder folder = mFolders.get(0);
                        folder.setCount(folder.getCount() + 1);
                    }
                    //分级
                    Folder folder = new Folder();
                    folder.setPath(folderFile.getAbsolutePath());
                    if (mFolders.contains(folder)) {
                        Folder f = mFolders.get(mFolders.indexOf(folder));
                        f.setCount(f.getCount() + 1);
                    } else {
                        folder.setName(folderFile.getName());
                        folder.setCover(image);
                        folder.setIsselected(false);
                        mFolders.add(folder);
                    }
                } while (data.moveToNext());
                if (!CommonUtil.isEmpty(mDefaults)) {
                    for (int i = 0; i < mDefaults.size(); i++) {
                        addSelected(mSelectedMaps.get(i));
                    }
                }
                mShowingImages.clear();
                mShowingImages.addAll(mAllImages);
                mPAdapter.notifyDataSetChanged();
            }
        }
        if (listener != null) {
            listener.onFinishLoad(mFolders, mAllImages);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public static class Listener {
        public void onStartLoad() {
        }

        public void onFinishLoad(ArrayList<Folder> folders, ArrayList<Image> allimages) {
        }

        public void onSelected(ArrayList<Image> selectedimages, int selectedcount, Image image) {
        }

        public void onRemove(ArrayList<Image> selectedimages, int selectedcount, Image image) {
        }

        public void onFolderSelected(Folder folder, ArrayList<Image> images_in) {
        }

        public void onImageClick(Image image) {
        }
    }

    public void showPhotos(Folder folder) {
        mShowingImages.clear();
        for (Image image : mAllImages) {
            if (image.getPath().contains(folder.getPath())) {
                mShowingImages.add(image);
            }
        }
        mPAdapter.notifyDataSetChanged();
    }

    public void showPhotos(String folder) {
        mShowingImages.clear();
        for (Image image : mAllImages) {
            if (image.getPath().contains(folder)) {
                mShowingImages.add(image);
            }
        }
        mPAdapter.notifyDataSetChanged();
    }

    public ArrayList<Folder> getFolders() {
        return mFolders;
    }

    private ArrayList<Image> mSelectedImages;

    private void addSelected(Image image) {
        if (mSelectedImages == null) {
            mSelectedImages = new ArrayList<>();
        }
        if (mSelectedImages.contains(image)) {
            return;
        }
        mSelectedImages.add(image);
    }

    private void removeSelected(Image image) {
        if (mSelectedImages == null) {
            return;
        }
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image);
        }
    }

    public ArrayList<Image> getSelectedImages() {
//        ArrayList<Image> images = new ArrayList<>();
//        for (Image image : mAllImages) {
//            if (image.isSelected()) {
//                images.add(image);
//            }
//        }
        return mSelectedImages;
    }

    public int getSelectedCount() {
        if (mSelectedImages == null) {
            return 0;
        }
        return mSelectedImages.size();
    }

    private ArrayList<String> mDefaults;

    public void setDefaults(ArrayList<String> defaults) {
        mDefaults = defaults;
    }

    private int maxCount = 9;

    public void setMaxCount(int count) {
        maxCount = count;
    }

    private class PhotoAdapter extends AbsBaseDataAdapter<PhotoHolder, Image> {
        public PhotoAdapter(Context context, ArrayList<Image> datas) {
            super(context, datas);
        }

        @Override
        public View createItemView(ViewGroup parent, int viewType) {
            return LayoutInflater.from(getContext()).inflate(R.layout.item_grid_multiimagepick, parent, false);
        }

        @Override
        protected PhotoHolder createViewHolder(ViewGroup parent, View itemView, int viewType) {
            return new PhotoHolder(itemView);
        }

        @Override
        protected void afterBindViewHolder(PhotoHolder holder, int position) {
            final Image image = getItem(position);
            Glide.with(getContext()).load(image.getPath()).override(250,250).dontAnimate().dontTransform().diskCacheStrategy(DiskCacheStrategy.RESULT).into(holder.iv_photo);
            if (image.isSelected()) {
                holder.iv_switch.setImageResource(R.drawable.multi_check_on);
                holder.v_trans.setVisibility(View.VISIBLE);
            } else {
                holder.iv_switch.setImageResource(R.drawable.multi_check_no);
                holder.v_trans.setVisibility(View.GONE);
            }
            holder.iv_photo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        listener.onImageClick(image);
                    }
                }
            });
            holder.checkRL.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (image.isSelected()) {
                        image.setSelected(false);
                        removeSelected(image);
                        if (listener != null) {
                            listener.onRemove(mSelectedImages, getSelectedCount(), image);
                        }
                    } else {
                        if (getSelectedCount() >= maxCount) {
                            return;
                        }
                        image.setSelected(true);
                        addSelected(image);
                        if (listener != null) {
                            listener.onSelected(mSelectedImages, getSelectedCount(), image);
                        }
                    }
                    mPAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    private class PhotoHolder extends android.support.v7.widget.RecyclerView.ViewHolder {
        //        @Inject(R.id.iv_photo)
        public ImageView iv_photo;
        //        @Inject(R.id.iv_switch)
        public ImageView iv_switch;
        //        @Inject(R.id.checkRL)
        public RelativeLayout checkRL;
        //        @Inject(R.id.v_trans)
        public View v_trans;

        public PhotoHolder(View itemView) {
            super(itemView);
            iv_photo = (ImageView) itemView.findViewById(R.id.iv_photo);
            iv_switch = (ImageView) itemView.findViewById(R.id.iv_switch);
            checkRL = (RelativeLayout) itemView.findViewById(R.id.checkRL);
            v_trans = itemView.findViewById(R.id.v_trans);
        }
    }

    private class FolderAdapter extends AbsBaseDataAdapter<FolderHolder, Folder> {
        public FolderAdapter(Context context, ArrayList<Folder> datas) {
            super(context, datas);
        }

        @Override
        protected FolderHolder createViewHolder(ViewGroup parent, View itemView, int viewType) {
            return null;
        }

        @Override
        protected void afterBindViewHolder(FolderHolder holder, int position) {

        }
    }

    private class FolderHolder extends android.support.v7.widget.RecyclerView.ViewHolder {

        public FolderHolder(View itemView) {
            super(itemView);
        }
    }
}
