const {ccclass, property} = cc._decorator;

@ccclass
export default class EnemyManagerControl extends cc.Component {
    
    @property(cc.Prefab)
    enemyPre: cc.Prefab = null;

    // LIFE-CYCLE CALLBACKS:

    // onLoad () {}

    start () {
        this.schedule(()=>{
           let enemy = cc.instantiate(this.enemyPre);
           enemy.setParent(cc.director.getScene());
           enemy.y = this.node.y;
           enemy.x = Math.random() * 400 +20
        },2)
    }

    // update (dt) {}
}
