
export class SupplierWithdraw {

    /**
       * 供应商提现ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 供应商（supplier表id）
     */
    supplierId:number;

    /**
       * 供应商名称
     */
    supplierName:string;

    /**
       * 提现金额
     */
    amount:number;

    /**
       * 账号类型（1线下支付2..）
     */
    cardType:number;

    /**
       * 账号/卡号/openID
     */
    cardNo:string;

    /**
       * 收款人
     */
    payee:string;

    /**
       * 申请状态（1待发放2发放异常）
     */
    state:number;

    /**
       * 出错说明
     */
    errorDescription:string;

    /**
       * 申请时间
     */
    applyTime:string;

    /**
       * 发放时间
     */
    dealTime:string;
}
