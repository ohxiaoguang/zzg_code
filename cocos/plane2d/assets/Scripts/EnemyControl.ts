
const {ccclass, property} = cc._decorator;

@ccclass
export default class EnemyControl extends cc.Component {

    isDie:boolean = false;
    // LIFE-CYCLE CALLBACKS:

    // onLoad () {}

    start () {

    }

    update (dt) {
        //console.log(this.isDie)
        if(this.isDie == false){
            this.node.y -= 200*dt
        }
        
        if(this.node.y < -850){
            this.node.destroy()
        }
    }

    die(){
        this.isDie = true

        cc.loader.loadRes("enemy0_die",cc.SpriteFrame,(err,res)=>{
            this.node.getComponent(cc.Sprite).spriteFrame = res

            
        })

        // console.log('die:',this.node)
        // console.log('die:',this.node.destroy)

        setTimeout(()=>{
            if(this.node){
                this.node.destroy()
            }
        },300)
    }
}
