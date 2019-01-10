import {Component, ComponentFactoryResolver, Input, OnInit, ViewChild, ViewContainerRef} from '@angular/core';
import {FormErrorComponent} from "../../form-error/form-error.component";

@Component({
    selector: 'app-dynamic-form-widget',
    templateUrl: './dynamic-form-widget.component.html',
    styleUrls: ['./dynamic-form-widget.component.scss']
})
export class DynamicFormWidgetComponent implements OnInit {
    /**
     * 表单控制项
     */
    @Input() control;

    /**
     * 组件配置项
     * {
   *    name: '...' // 配置项名称
   *    type: '...' // 表单类型
   *    errors: '...' // 错误提示信息
   * }
     */
    @Input() options: {};

    /**
     * 组件视图容器
     */
    @ViewChild('widget', {read: ViewContainerRef}) widget;

    /**
     * 表单控件组件的引用
     */
    controlRef;

    /**
     * 错误组件的引用
     */
    errorsRef;

    constructor(private componentFactoryResolver: ComponentFactoryResolver) {
    }

    ngOnInit() {
    }

    ngAfterViewInit() {
        setTimeout(() => {
            this.loadComponent();
        }, 0);
    }

    loadComponent() {
        // 加载表单控件
        this.widget.clear();
        const controlComponentFactory = this.componentFactoryResolver.resolveComponentFactory(this.options['type']);
        this.controlRef = this.widget.createComponent(controlComponentFactory);
        this.controlRef.instance.control = this.control;
        this.controlRef.instance.options = this.options;
        // 加载错误组件
        const errorsComponentFactory = this.componentFactoryResolver.resolveComponentFactory(FormErrorComponent);
        this.errorsRef = this.widget.createComponent(errorsComponentFactory);
        this.errorsRef.instance.control = this.control;
        this.errorsRef.instance.formErrors = this.options['errors'];
    }

    ngOnDestroy() {
        if (this.widget) {
            this.widget.clear();
        }

        if (this.controlRef) {
            this.controlRef.destroy();
        }

        if (this.errorsRef) {
            this.errorsRef.destroy();
        }
    }
}
