import EnemyControl from "./EnemyControl";

const {ccclass, property} = cc._decorator;

@ccclass
export default class BulletControl extends cc.Component {

    @property
    speed:number = 800;

    // LIFE-CYCLE CALLBACKS:

    // onLoad () {}

    start () {

    }

    update (dt) {
        this.node.y += this.speed*dt
        if(this.node.y > 820){
            this.node.destroy()
        }
    }

    onCollisionEnter(other){
        console.debug(11111111111)
        if(other.tag == 1){
            other.getComponent(EnemyControl).die()
            this.node.destroy();
        }
    }

}
