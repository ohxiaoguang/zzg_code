const {ccclass, property} = cc._decorator;

@ccclass
export default class KeyBoardControl extends cc.Component {

    @property(cc.Prefab)
    btn: cc.Prefab = null;


    // LIFE-CYCLE CALLBACKS:

    // onLoad () {}

    start () {
        for(let i=1;i<=9;i++){
            const btn1:cc.Node = cc.instantiate(this.btn);
            btn1.setParent(this.node)
            btn1.getComponentInChildren(cc.Label).string = i+''
            btn1.on(cc.Node.EventType.TOUCH_END,  (touchEvent) => {
                cc.director.getScene().getComponentInChildren("ScreenControl").click1(i);
                cc.log('点击事件在此');
             })
        }
        const btn1:cc.Node = cc.instantiate(this.btn);
        btn1.setParent(this.node)
        btn1.getComponentInChildren(cc.Label).string = 0+''
        btn1.on(cc.Node.EventType.TOUCH_END,  (touchEvent) => {
            cc.director.getScene().getComponentInChildren("ScreenControl").click1(0);
            cc.log('点击事件在此');
         })
    }

    // update (dt) {}
}
