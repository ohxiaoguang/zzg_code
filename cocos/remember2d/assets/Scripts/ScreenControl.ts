const {ccclass, property} = cc._decorator;

@ccclass
export default class ScreenControl extends cc.Component {

    @property(cc.Label)
    label: cc.Label = null;

    @property
    text: string = 'hello';


    // 总题目数组
    arr1 = [];
    // 每道题倒计时
    timer:cc.Label;
    // 每道题结果
    result:cc.Label;
    // 总分
    score:cc.Label;
    row1:cc.Label[]=[];
    row2:cc.Label[]=[];

    idx1:number = 0;
    idx2:number = -2;

    timerNum:number = 3;

    // LIFE-CYCLE CALLBACKS:

    onLoad () {
        // 获取组件
        const top = this.node.getChildByName('top');
        this.timer = top.getChildByName('timer').getComponent(cc.Label)
        this.result = top.getChildByName('result').getComponent(cc.Label)
        this.score = top.getChildByName('score').getComponent(cc.Label)

        const row1 = this.node.getChildByName('row1');
        this.row1.push(row1.getChildByName('s0').getChildByName('label').getComponent(cc.Label))
        this.row1.push(row1.getChildByName('s1').getChildByName('label').getComponent(cc.Label))
        this.row1.push(row1.getChildByName('s2').getChildByName('label').getComponent(cc.Label))
        this.row1.push(row1.getChildByName('s3').getChildByName('label').getComponent(cc.Label))
        this.row1.push(row1.getChildByName('s4').getChildByName('label').getComponent(cc.Label))
        this.row1.push(row1.getChildByName('s5').getChildByName('label').getComponent(cc.Label))

        const row2 = this.node.getChildByName('row2');
        this.row2.push(row2.getChildByName('s0').getChildByName('label').getComponent(cc.Label))
        this.row2.push(row2.getChildByName('s1').getChildByName('label').getComponent(cc.Label))
        this.row2.push(row2.getChildByName('s2').getChildByName('label').getComponent(cc.Label))
        this.row2.push(row2.getChildByName('s3').getChildByName('label').getComponent(cc.Label))
        this.row2.push(row2.getChildByName('s4').getChildByName('label').getComponent(cc.Label))
        this.row2.push(row2.getChildByName('s5').getChildByName('label').getComponent(cc.Label))
        
        // 初始化题目
        while (this.arr1.length<100) {
            const num1 = Math.floor(Math.random()*10);
            const num2 = Math.floor(Math.random()*10);
            if (num1+num2 <10) {
                this.arr1.push([num1,'+',num2,num1+num2]);
            }else{
                if (num1>num2) {
                    this.arr1.push([num1,'-',num2,num1-num2]);
                }else{
                    this.arr1.push([num2,'-',num1,num2-num1]);
                }
            }
        }
    }

    start () {
        this.schedule(()=>{
            if(this.timerNum<0){
                if(this.arr1[this.idx2]){

                    this._fillRow(this.row2,
                        '第'+(this.idx2+1)+'题',
                        this.arr1[this.idx2][0],
                        this.arr1[this.idx2][1],
                        this.arr1[this.idx2][2],
                        '=',this.arr1[this.idx2][3]);

                    this.result.string = '超时'
                    setTimeout(() => {
                        this.nextRow()
                    }, 1000);

                }else{
                    this.idx1++;
                    this.idx2++;
                    this.nextRow()
                }
            }else{
                this.timer.string = this.timerNum+''
                this.timerNum--;

            }


        },1)
        
        this.nextRow()
    }

    nextRow(){
        this.result.string = '-'
        this.timerNum = 3;
        if(this.arr1[this.idx1]){
            this._fillRow(this.row1,
                '第'+(this.idx1+1)+'题',
                this.arr1[this.idx1][0],
                this.arr1[this.idx1][1],
                this.arr1[this.idx1][2],'=','?');
        }else{
            this._fillRow(this.row1,'-','-','-','-','-','-');
        }

        if(this.arr1[this.idx2]){
            this._fillRow(this.row2,'第'+(this.idx2+1)+'题','?','?','?','=','');
        }else{
            this._fillRow(this.row2,'-','-','-','-','-','-');
        }

    }

    click1(btnNum:number){
        if(this.arr1[this.idx2]){
            this._fillRow(
                this.row2,
                '第'+(this.idx2+1)+'题',
                this.arr1[this.idx2][0],
                this.arr1[this.idx2][1],
                this.arr1[this.idx2][2],
                '=',
                this.arr1[this.idx2][3]
                );

            if(btnNum == this.arr1[this.idx2][3]){
                this.result.string = '正确'
                this.score.string = String(Number(this.score.string)+1)
            }else{
                this.result.string = '错误'
            }
            setTimeout(() => {
                this.idx1++;
                this.idx2++;
                this.nextRow()
            }, 1000);
        }
    }

    _fillRow(row:cc.Label[],val1,val2,val3,val4,val5,val6){
        row[0].string = val1;
        row[1].string = val2;
        row[2].string = val3;
        row[3].string = val4;
        row[4].string = val5;
        row[5].string = val6;
    }

    // update (dt) {}
}
