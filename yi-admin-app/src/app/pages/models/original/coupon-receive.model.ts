
export class CouponReceive {

    /**
       * ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 优惠券（coupon表ID）
     */
    couponId:number;

    /**
       * 优惠码
     */
    couponNo:string;

    /**
       * 面值
     */
    parValue:number;

    /**
       * 使用
     */
    use:number;

    /**
       * 剩余
     */
    surplus:number;

    /**
       * 会员（member表ID）
     */
    memberId:number;

    /**
       * 会员账号
     */
    memberPhone:string;

    /**
       * 领取方式（1手工发放2自助领取）
     */
    receiveMode:boolean;

    /**
       * 领取时间
     */
    receiveTime:string;

    /**
       * 开始时间
     */
    startTime:string;

    /**
       * 结束时间
     */
    endTime:string;

    /**
       * 状态（1未使用2已使用3已失效）
     */
    state:boolean;

    /**
       * 使用时间
     */
    useTime:string;

    /**
       * 订单（order表ID）
     */
    orderId:number;

    /**
       * 订单编号
     */
    orderNo:string;

    /**
       * 删除（0否1是）
     */
    deleted:boolean;

    /**
       * 删除时间
     */
    delTime:string;
}
