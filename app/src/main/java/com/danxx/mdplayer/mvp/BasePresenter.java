package com.danxx.mdplayer.mvp;

import rx.subscriptions.CompositeSubscription;

/**
 * Description：BasePresenter
 * <p>
 * Base class that implements the Presenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 * <p>
 */
public class BasePresenter<T extends MvpView> implements Presenter<T> {

    private T mMvpView;
    /**CompositeSubscription来持有所有的Subscriptions，然后在onDestroy()或者onDestroyView()里取消所有的订阅。**/
    public CompositeSubscription mCompositeSubscription;

    /**
     *
     * @param mvpView  把Activity或者Fragment的引用复制过来，方便在调用View接口做回调
     */
    @Override
    public void attachView(T mvpView) {
        this.mMvpView = mvpView;
        /****/
        this.mCompositeSubscription = new CompositeSubscription();
    }


    @Override
    public void detachView() {
        this.mMvpView = null;
        /**取消Subscriber事件订阅**/
        this.mCompositeSubscription.unsubscribe();
        this.mCompositeSubscription = null;
    }


    public boolean isViewAttached() {
        return mMvpView != null;
    }


    public T getMvpView() {
        return mMvpView;
    }


    public void checkViewAttached() {
        if (!isViewAttached()) throw new MvpViewNotAttachedException();
    }


    public static class MvpViewNotAttachedException extends RuntimeException {
        public MvpViewNotAttachedException() {
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}