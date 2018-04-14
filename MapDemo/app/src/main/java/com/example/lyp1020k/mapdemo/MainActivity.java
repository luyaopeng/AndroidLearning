package com.example.lyp1020k.mapdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapModelCross;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.autonavi.tbt.TrafficFacilityInfo;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements AMapNaviViewListener, AMapNaviListener {

    Context context;
    AMapNaviView mAMapNaviView;
    AMapNavi mAMapNavi;
    NaviLatLng mEndLatlng = new NaviLatLng(39.96087, 116.45798);
    NaviLatLng mStartLatlng = new NaviLatLng(39.904556, 116.427231);
    List<NaviLatLng> mStartList = new ArrayList<NaviLatLng>();
    List<NaviLatLng> mEndList = new ArrayList<NaviLatLng>();
    List<NaviLatLng> mWayPointList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        mAMapNavi.addAMapNaviListener(this);
        mAMapNavi.setEmulatorNaviSpeed(60);
        mAMapNavi.startNavi(AMapNavi.EmulatorNaviMode);

        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.setAMapNaviViewListener(this);
        mAMapNaviView.onCreate(savedInstanceState);

        setAmapNaviViewOptions();

 //       AmapNaviPage.getInstance().showRouteActivity(context, new AmapNaviParams(null), IndexActivity.class);
    }

    private void setAmapNaviViewOptions() {
        if (mAMapNaviView == null) {
            return;
        }
        AMapNaviViewOptions viewOptions = new AMapNaviViewOptions();
        viewOptions.setSettingMenuEnabled(false);//设置菜单按钮是否在导航界面显示
        viewOptions.setNaviNight(false);//设置导航界面是否显示黑夜模式
        viewOptions.setReCalculateRouteForYaw(true);//设置偏航时是否重新计算路径
        viewOptions.setReCalculateRouteForTrafficJam(true);//前方拥堵时是否重新计算路径
        viewOptions.setTrafficInfoUpdateEnabled(true);//设置交通播报是否打开
        viewOptions.setCameraInfoUpdateEnabled(true);//设置摄像头播报是否打开
        viewOptions.setScreenAlwaysBright(true);//设置导航状态下屏幕是否一直开启。
  //      viewOptions.setCrossDisplayEnabled(false); //设置导航时 路口放大功能是否显示
        viewOptions.setTrafficBarEnabled(false);  //设置 返回路况光柱条是否显示（只适用于驾车导航，需要联网）
        viewOptions.setMonitorCameraEnabled(true); //设置摄像头图标是否显示 是
        viewOptions.setModeCrossDisplayShow(true);
        // viewOptions.setLayoutVisible(false);  //设置导航界面UI是否显示
        //viewOptions.setNaviViewTopic(mThemeStle);//设置导航界面的主题
        //viewOptions.setZoom(16);
        viewOptions.setTilt(0);  //2D显示
        mAMapNaviView.setViewOptions(viewOptions);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAMapNaviView.onResume();
        mStartList.add(mStartLatlng);
        mEndList.add(mEndLatlng);
    }

    @Override
    protected void onPause() {
        super.onPause();
 //       mAMapNaviView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAMapNaviView.onDestroy();

        mAMapNavi.stopNavi();
        mAMapNavi.destroy();
    }

    @Override
    public void onGetNavigationText(String s) {

    }

    @Override
    public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

    }

    @Override
    public void updateIntervalCameraInfo(AMapNaviCameraInfo aMapNaviCameraInfo, AMapNaviCameraInfo aMapNaviCameraInfo1, int i) {

    }

    @Override
    public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

    }

    @Override
    public void showModeCross(AMapModelCross aMapModelCross) {

    }

    @Override
    public void hideModeCross() {

    }

    @Override
    public void showLaneInfo(AMapLaneInfo aMapLaneInfo) {

    }

    @Override
    public void onCalculateRouteSuccess(int[] ints) {

    }

    @Override
    public void onPlayRing(int i) {

    }

    /**
     * 导航创建失败时的回调函数
     */
    @Override
    public void onInitNaviFailure() {
        Log.e("nav", "导航创建失败" );
    }

    /**
     * 导航创建成功时的回调函数
     */
    @Override
    public void onInitNaviSuccess() {
        /**
         * 方法:
         *   int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute);
         * 参数:
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         * 说明:
         *      以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         * 注意:
         *      不走高速与高速优先不能同时为true
         *      高速优先与避免收费不能同时为true
         */
        int strategy=0;
        try {
            strategy = mAMapNavi.strategyConvert(true,false,false,false, false);  //自己设置。。
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(mStartList, mEndList, mWayPointList, strategy);
        Log.e("nav", "导航创建成功" );
    }

    /**
     * type - 导航类型，1 ： 实时导航，2 ：模拟导航
     * @param type
     */
    @Override
    public void onStartNavi(int type) {
        Log.e("nav", "启动导航后回调函数"+type );
    }

    /**
     * 当前方路况光柱信息有更新时回调函数。
     */
    @Override
    public void onTrafficStatusUpdate() {

    }

    /**
     * 当GPS位置有更新时的回调函数
     * @param location
     */
    @Override
    public void onLocationChange(AMapNaviLocation location) {

    }

    /**
     * 导航播报信息回调函数
     * type - 播报类型，包含导航播报、前方路况播报和整体路况播报，类型请见NaviTTSType
     * text - 播报文字
     * @param type
     * @param text
     */
    @Override
    public void onGetNavigationText(int type, String text) {
        String NavInformationText = text;  //测试---fan
    }

    /**
     * 模拟导航停止后回调函数
     */
    @Override
    public void onEndEmulatorNavi() {
        Log.e("nav","模拟导航停止");
    }

    /**
     * 到达目的地后回调函数
     */
    @Override
    public void onArriveDestination() {
        Log.e("nav", "到达目的地");

    }

    /**
     * 步行或者驾车路径规划失败后的回调函数
     * @param errorInfo
     */
    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        Log.e("nav", "驾车路径规划失败" +errorInfo);
    }

    /**
     * 步行或驾车导航时,出现偏航后需要重新计算路径的回调函数
     */
    @Override
    public void onReCalculateRouteForYaw() {

    }

    /**
     * 驾车导航时，如果前方遇到拥堵时需要重新计算路径的回调
     */
    @Override
    public void onReCalculateRouteForTrafficJam() {

    }

    /**
     * 驾车路径导航到达某个途经点的回调函数
     * wayID - 到达途径点的编号，标号从1开始，依次累加。 模拟导航下不工作
     * @param wayID
     */
    @Override
    public void onArrivedWayPoint(int wayID) {

    }

    /**
     * 用户手机GPS设置是否开启的回调函数
     * enabled - true,开启;false,未开启
     * @param enabled
     */
    @Override
    public void onGpsOpenStatus(boolean enabled) {
    }


    @Override
    public void onNaviInfoUpdated(AMapNaviInfo naviInfo) {


    }


    /**
     * 导航数据回调  ********重点
     * 导航引导信息回调 naviinfo 是导航信息类
     * naviinfo - 导航信息对象。
     * @param naveinfo
     */
    @Override
    public void onNaviInfoUpdate(NaviInfo naveinfo) {
        if (naveinfo == null) {
            return;
        }
//  很多的数据 ，在这里得到
// 算是重点区域吧
    }



    @Override
    public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

    }

    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

    }

    /**
     * 显示路口放大图回调
     * aMapNaviCross - 路口放大图类，可以获得此路口放大图bitmap
     * @param aMapNaviCross
     */
    @Override
    public void showCross(AMapNaviCross aMapNaviCross) {
    }

    /**
     * 关闭路口放大图回调
     */
    @Override
    public void hideCross() {
    }

    /**
     * 显示道路信息回调
     * laneInfos - 道路信息数组，可获得各条道路分别是什么类型，可用于用户使用自己的素材完全自定义显示。
     * laneBackgroundInfo - 道路背景数据数组，可用于装载官方的DriveWayView，并显示。
     * laneRecommendedInfo - 道路推荐数据数组，可用于装载官方的DriveWayView，并显示。
     * @param laneInfos
     * @param laneBackgroundInfo
     * @param laneRecommendedInfo
     */
    @Override
    public void showLaneInfo(AMapLaneInfo[] laneInfos, byte[] laneBackgroundInfo, byte[] laneRecommendedInfo) {

    }

    /**
     * 关闭道路信息回调
     */
    @Override
    public void hideLaneInfo() {

    }

    /**
     * 多路线算路成功回调
     * */
//    @Override
//    public void onCalculateMultipleRoutesSuccess(int[] ints) {
//        Log.e("nav","多路线算路成功回调");
//    }

    /**
     * 通知当前是否显示平行路切换
     * parallelRoadType - 0表示隐藏 1 表示显示主路 2 表示显示辅路
     * @param i
     */
    @Override
    public void notifyParallelRoad(int i) {

    }

    /**
     * 巡航模式（无路线规划）下，道路设施信息更新回调
     *  infos - 道路设施信息
     * @param aMapNaviTrafficFacilityInfos
     */
    @Override
    public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

    }

    /**
     * 巡航模式（无路线规划）下，统计信息更新回调 连续5个点大于15km/h后开始回调
     * aimLessModeStat - 巡航模式（无路线规划）下统计信息
     * @param aimLessModeStat
     */
    @Override
    public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

    }


    /**
     * 巡航模式（无路线规划）下，统计信息更新回调 当拥堵长度大于500米且拥堵时间大于5分钟时回调
     * @param aimLessModeCongestionInfo
     */
    @Override
    public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

    }


    /**==================================================以下为AMapNaviViewListener监听回调======================================*/

    /**
     * 界面右下角功能设置按钮的回调接口
     */
    @Override
    public void onNaviSetting() {
    }

    /**
     * 导航页面左下角返回按钮点击后弹出的『退出导航对话框』中选择『确定』后的回调接口
     */
    @Override
    public void onNaviCancel() {
        finish();
    }

    /**
     * 导航页面左下角返回按钮的回调接口 false-由SDK主动弹出『退出导航』对话框，true-SDK不主动弹出『退出导航对话框』，由用户自定义
     * @return
     */
    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    /**
     * 导航界面地图状态的回调
     * isLock - 地图状态，0:车头朝上状态；1:非锁车状态,即车标可以任意显示在地图区域内。
     * @param isLock
     */
    @Override
    public void onNaviMapMode(int isLock) {

    }

    /**
     * 界面左上角转向操作的点击回调
     */
    @Override
    public void onNaviTurnClick() {

    }

    /**
     * 界面下一道路名称的点击回调
     */
    @Override
    public void onNextRoadClick() {

    }

    /**
     * 界面全览按钮的点击回调
     */
    @Override
    public void onScanViewButtonClick() {

    }

    /**
     * 是否锁定地图的回调
     * @param b
     */
    @Override
    public void onLockMap(boolean b) {

    }

    /**
     * 导航view加载完成回调
     */
    @Override
    public void onNaviViewLoaded() {

    }

    @Override
    protected void onStop() {
        super.onStop();

    }

}
