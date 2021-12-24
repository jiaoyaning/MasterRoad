
# 四级缓存
## Scrap (mChangedScrap , mAttachedScrap)
mChangedScrap仅参与预布局，
mAttachedScrap存放还会被复用的ViewHolder

## Cached (mCachedViews)
最多存放2个缓存ViewHolder

## Extension (mViewCacheExtension)
需开发者自定义实现

## Pool(mRecyclerPool)
可以理解RecyclerPool是(int,ArrayList<ViewHolder>)的SparseArray，键是viewType，每个viewType最多可以存放5个ViewHolder