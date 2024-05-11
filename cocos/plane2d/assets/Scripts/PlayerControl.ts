const {ccclass, property} = cc._decorator;

@ccclass
export default class PlayerControl extends cc.Component {
    @property(cc.Prefab)
    bulletPre:cc.Prefab = null;

    // LIFE-CYCLE CALLBACKS:

    // onLoad () {}

    start () {
        // 移动
        this.node.on(cc.Node.EventType.TOUCH_MOVE,(e)=>{
            this.node.setPosition(e.getLocation())
        })

        // 攻击 计时器
        this.schedule(()=>{
            let bullet: cc.Node = cc.instantiate(this.bulletPre);
            bullet.setParent(cc.director.getScene());
            bullet.setPosition(
                this.node.x,
                this.node.y + 80
            );
        },0.3)
        
        // 开启碰撞检测
        cc.director.getCollisionManager().enabled = true
    }

    // update (dt) {}
}
