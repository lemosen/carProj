
export class SupplierCheckAccount {

    /**
       * 供应商对账ID
     */
    id:number;

    /**
       * GUID
     */
    guid:string;

    /**
       * 供应商（supplier表ID）
     */
    supplierId:number;

    /**
       * 供应商名称
     */
    supplierName:string;

    /**
       * 订单（sale_order表ID）
     */
    saleOrderId:number;

    /**
       * 订单编号
     */
    saleOrderNo:string;

    /**
       * 下单时间
     */
    orderTime:string;

    /**
       * 报考（product表ID）
     */
    productId:number;

    /**
       * 报考编号
     */
    productNo:string;

    /**
       * 报考名称
     */
    productName:string;

    /**
       * 供货价
     */
    supplyPrice:number;

    /**
       * 数量
     */
    quantity:number;

    /**
       * 应结货款
     */
    settlementAmount:number;

    /**
       * 结算时间
     */
    settlementTime:number;
}
