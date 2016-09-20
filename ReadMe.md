> 学习Ansen的博客，原文：[带你实现开发者头条APP(五)--RecyclerView下拉刷新上拉加载 ](http://blog.csdn.net/lowprofile_coding/article/details/51321896),这一篇写的很详细。

# 知识点 #
>今天主要是实现recyclerview的上拉加载跟多和下拉刷新，依赖的项目是[CommonPullToRefresh](https://github.com/Chanven/CommonPullToRefresh),由于我们要加入轮播图，需要修改源码看，所以依赖采用import module的形式，而不是在build.gradle中配置
```
compile 'com.chanven.lib:cptr:1.1.0'
```

见图：
![这里写图片描述](http://img.blog.csdn.net/20160920225620849)

效果图：
![这里写图片描述](http://img.blog.csdn.net/20160920231837660)
## 布局中怎么引用？ ##
> 直接在用PtrClassicFrameLayout包裹recyclerview，各属性github上介绍的很详细。

```
<com.chanven.lib.cptr.PtrClassicFrameLayout
    android:id="@+id/ptrFlameLayout"
    xmlns:cube_ptr="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="#f0f0f0"
    cube_ptr:ptr_duration_to_close="200"
    cube_ptr:ptr_duration_to_close_header="1000"
    cube_ptr:ptr_keep_header_when_refresh="true"
    cube_ptr:ptr_pull_to_fresh="false"
    cube_ptr:ptr_ratio_of_header_height_to_refresh="1.2"
    cube_ptr:ptr_resistance="1.7">

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical">

    </android.support.v7.widget.RecyclerView>
</com.chanven.lib.cptr.PtrClassicFrameLayout>
```
## 使用步骤 ##
- 对原adapter进行封装
- 将轮播图的view添加到新adapter
- 给recyclerview设置adapter
- 给PtrClassicFrameLayout设置下拉刷新监听
- 给PtrClassicFrameLayout设置上拉加载更多的监听
- 给PtrClassicFrameLayout设置可以加载更多
> 原adapter必须继承RecyclerView.Adapter<>，泛型必须是RecyclerView.ViewHolder，不能是：

```
MyRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.MyViewHolder>
```

```
adapter = new MyRecyclerAdapter(context, list);
mAdapter = new RecyclerAdapterWithHF((RecyclerView.Adapter)adapter);
mAdapter.addCarousel(initHeadView());
recyclerView.setAdapter(mAdapter);

ptrFlameLayout.setPtrHandler(ptrDefaultHandler);
ptrFlameLayout.setOnLoadMoreListener(onLoadMoreListener);
ptrFlameLayout.setLoadMoreEnable(true);
```
## 怎么实现顶部加入轮播图 ？##
> 需要对RecyclerAdapterWithHF进行改造

- 定义一个集合保存轮播图的view，如果轮播图只有一个，可以用一个变量View
- 定义轮播图类型type（int）
- 在getViewtType(..)中返回轮播图类型
- 在onCreateViewHolder(..)中修改一点，就是在if判断中非HeadView + 非FootView + 非Carousel都要加入framelayout
- 在onBindViewHolder(..)绑定
- 对外提供一个方法：把轮播图增加到adapter

### 1 定义一个集合保存轮播图的view，如果轮播图只有一个，可以用一个变量View ###
```
private List<View> mHeaders = new ArrayList<View>();
private List<View> mFooters = new ArrayList<View>();
private List<View> mCarousel = new ArrayList<View>();//存放轮播图的集合
```
### 2 定义轮播图类型type（int） ###
```
public static final int TYPE_HEADER = 7898;
public static final int TYPE_FOOTER = 7899;
public static final int TYPE_CAROUSEL = 7900;//轮播图的viewtype
```
### 3 在getViewtType(..)中返回轮播图类型 ###
增加的代码：
```
else if (isCarousel(position)) {//是则返回轮播图类型
    return TYPE_CAROUSEL;
}
```
增加后：
```
@Override
public final int getItemViewType(int position) {
    // check what type our position is, based on the assumption that the
    // order is headers > items > footers
    if (isHeader(position)) {
        return TYPE_HEADER;
    } else if (isCarousel(position)) {//是则返回轮播图类型
        return TYPE_CAROUSEL;
    } else if (isFooter(position)) {
        return TYPE_FOOTER;
    }
    int type = getItemViewTypeHF(getRealPosition(position));
    if (type == TYPE_HEADER || type == TYPE_FOOTER) {
        throw new IllegalArgumentException("Item type cannot equal " + TYPE_HEADER + " or " + TYPE_FOOTER);
    }
    return type;
}
```
其中isCarousel(position)是
```
//判断是不是轮播图isCarousel
private boolean isCarousel(int position) {
    return (mCarousel.size() > 0 && position == mHeaders.size());
}
```
作者判断是不是头尾的代码：
```
private boolean isHeader(int position) {
    return (position < mHeaders.size());
}

private boolean isFooter(int position) {
    return (position >= mHeaders.size() + getItemCountHF());
}
```
### 4 在onCreateViewHolder(..)中修改一点，就是在if判断中非HeadView + 非FootView + 非Carousel都要加入framelayout ###
增加的代码：

```
type != TYPE_CAROUSEL
```
增加后：
```
@Override
public final RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int type) {
    // if our position is one of our items (this comes from
    // getItemViewType(int position) below)
    if (type != TYPE_HEADER && type != TYPE_FOOTER && type != TYPE_CAROUSEL) {//不是轮播图类型
        ViewHolder vh = onCreateViewHolderHF(viewGroup, type);
        return vh;
        // else we have a header/footer
    } else {
        // create a new framelayout, or inflate from a resource
        FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
        // make sure it fills the space
        frameLayout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup
                .LayoutParams.WRAP_CONTENT));
        return new HeaderFooterViewHolder(frameLayout);
    }
}
```

### 5 在onBindViewHolder(..)绑定 ###
增加的代码：
```
//headView是0，下一个的position是mHead.size()
else if (mCarousel.size() > 0 && position == mHeaders.size()) {
    //取出轮播图：
    View v = mCarousel.get(position);
    prepareHeaderFooter((HeaderFooterViewHolder) vh, v);
}
```
增加后：
```
 @Override
public final void onBindViewHolder(final RecyclerView.ViewHolder vh, int position) {
    // check what type of view our position is
    if (isHeader(position)) {
        View v = mHeaders.get(position);
        // add our view to a header view and display it
        prepareHeaderFooter((HeaderFooterViewHolder) vh, v);
    } else if (mCarousel.size() > 0 && position == mHeaders.size()) {//headView是0，下一个的position是mHead.size()
        //取出轮播图：
        View v = mCarousel.get(position);
        prepareHeaderFooter((HeaderFooterViewHolder) vh, v);
    } else if (isFooter(position)) {
        View v = mFooters.get(position - getItemCountHF() - mHeaders.size());
        // add our view to a footer view and display it
        prepareHeaderFooter((HeaderFooterViewHolder) vh, v);
    } else {
        vh.itemView.setOnClickListener(new MyOnClickListener(vh));
        vh.itemView.setOnLongClickListener(new MyOnLongClickListener(vh));
        // it's one of our items, display as required
        onBindViewHolderHF(vh, getRealPosition(position));
    }
}
```
### 6 对外提供一个方法：把轮播图增加到adapter  ###
```
//add a carousel to the adapter
public void addCarousel(View carousel) {
     mCarousel.add(carousel);
}
```
或者：
```
//add a carousel to the adapter
public void addCarousel(View carousel) {
    if(!mCarousel.contains(carousel)){
        mCarousel.add(carousel);
        notifyItemInserted(mHeaders.size());
    }
}
```
## 怎么实现下拉刷新？ ##

```
ptrFlameLayout.setPtrHandler(ptrDefaultHandler);

//下拉刷新
private PtrDefaultHandler ptrDefaultHandler = new PtrDefaultHandler() {
    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        initData();//初始化数据
        mAdapter.notifyDataSetChanged();//更新ui
        ptrFlameLayout.refreshComplete();//刷新完成
    }
};
```
## 怎么实现上拉加载更多？ ##
```
ptrFlameLayout.setOnLoadMoreListener(onLoadMoreListener);

//上拉加载更多
private OnLoadMoreListener onLoadMoreListener = new OnLoadMoreListener() {
    @Override
    public void loadMore() {
        mHandler.sendEmptyMessageDelayed(0,3000);
    }
};


private Handler mHandler = new Handler(){
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Frag1Bean bean = new Frag1Bean();
        bean.id = 100;
        bean.title = "新增数据";
        list.add(bean);
        mAdapter.notifyDataSetChanged();
        ptrFlameLayout.loadMoreComplete(true);
    }
};
```
## 对第4天的adapter的修改 ##
> 由于day04我们实现了头部增加了轮播图的操作，现在要把它去掉，下文注释掉的就是需要去掉的

```
@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        if (headView != null && viewType == TYPE_HEADVIEW) {
//            return new MyViewHolder(headView);
//        }
    View view = LayoutInflater.from(context).inflate(R.layout.item_frag1, parent, false);
    return new MyViewHolder(view);
}


@Override
public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
    MyViewHolder holder = (MyViewHolder) viewHolder;
//        if (getItemViewType(position) == TYPE_HEADVIEW){
//            return;
//        }

    Frag1Bean bean = list.get(position);
    holder.tv_title.setText("" + bean.title);
    holder.tv_like.setText("" + bean.likeNumbers);
    holder.tv_comment.setText("" + bean.commentsNumbers);
}

//    @Override
//    public int getItemViewType(int position) {
//        if (headView == null) {
//            return TYPE_NORMAL;
//        }
//        if (position == 0) {
//            return TYPE_HEADVIEW;
//        }
//        return TYPE_NORMAL;
//    }


    public MyViewHolder(View itemView) {
        super(itemView);
//            if (itemView == headView) {
//                return;
//            }
        tv_title = (TextView) itemView.findViewById(R.id.tv_item_title);
        tv_like = (TextView) itemView.findViewById(R.id.tv_item_like);
        tv_comment = (TextView) itemView.findViewById(R.id.tv_item_comment);
    }
```
##解决滑动冲突##
>由于我把Toolbar放在了HomeFragment中，导致Frag1中的coordinateLayout不在一个布局中，所以无法我就在frag1中加了TextView来演示“滑动顶部固定”的效果，没出现滑动冲突，大家有冲突的话看作者的原博吧。
>另外，我在android4.1.1上coordinateLayout没有效果，在android5.1有效果。
# 源码下载 #
[DevelopHeadLine5](https://github.com/s1168805219/DevelopHeadLine5)

# 感谢原博主Asen #
原博链接：
[带你实现开发者头条(一) 启动页实现 ](http://blog.csdn.net/lowprofile_coding/article/details/51170252)
[ 带你实现开发者头条(二) 实现左滑菜单 ](http://blog.csdn.net/lowprofile_coding/article/details/51186965)
[带你实现开发者头条APP(三) 首页实现 ](http://blog.csdn.net/lowprofile_coding/article/details/51194577)
[带你实现开发者头条APP(四)---首页优化(加入design包)](http://blog.csdn.net/lowprofile_coding/article/details/51236496)
[带你实现开发者头条APP(五)--RecyclerView下拉刷新上拉加载 ](http://blog.csdn.net/lowprofile_coding/article/details/51321896)
