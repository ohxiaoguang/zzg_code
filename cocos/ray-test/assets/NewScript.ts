const {ccclass, property} = cc._decorator;

@ccclass
export default class NewClass extends cc.Component {

    // 方向
    dir:cc.Vec2 = cc.v2(0,1)

    // LIFE-CYCLE CALLBACKS:

    onLoad () {

        cc.director.getPhysicsManager().enabled=true;
    }

    start () {

    }

     update (dt) {
        this.node.x += this.dir.x * 100 * dt;
        this.node.y += this.dir.y * 100 * dt;

        let res = cc.director.getPhysicsManager().rayCast(this.node.getPosition(),cc.v2(this.node.x+this.dir.x*100,this.node.y+this.dir.y*100),cc.RayCastType.Closest);
        if(res.length>0){
            this.dir.y*=-1
        }
     }
}
