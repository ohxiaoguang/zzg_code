const {ccclass, property} = cc._decorator;

@ccclass
export default class BgControl extends cc.Component {

    // LIFE-CYCLE CALLBACKS:

    // onLoad () {}

    start () {

    }

    update (dt) {
        for(const item of this.node.children){
            item.y -= 50*dt;

            if(item.y < -850){
                item.y+=852*2;
            }
        }
    }
}
