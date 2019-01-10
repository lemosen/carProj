import {Component, ElementRef, EventEmitter, Input, Output} from '@angular/core';
import {Observable} from 'rxjs/Rx';
import 'rxjs/Observable';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import {BehaviorSubject} from "rxjs/BehaviorSubject";

@Component({
    selector: 'app-search',
    templateUrl: './search.component.html',
    styleUrls: ['./search.component.scss']
})
export class SearchComponent {
    @Input() dynamicStyle: string = '';
    @Input() showButton: boolean = true;
    @Input() title: string;
    @Input() placeholder: string = '搜索';
    @Input() searchValue: string;
    @Input() delay: number = 1000;

    @Output() onSearch: EventEmitter<String> = new EventEmitter();
    searchClick: BehaviorSubject<String> = new BehaviorSubject(null);

    constructor(private elementRef: ElementRef) {
        // 汇集输入框和查询按钮事件, 延时处理并避免重复触发
        const event$ = Observable.fromEvent(elementRef.nativeElement, 'keyup')
            .map(() => this.searchValue)
            .debounceTime(this.delay);

        Observable.merge(event$, this.searchClick.asObservable())
            .distinctUntilChanged()
            .subscribe(input => this.onSearch.emit(input));
    }

    doSearch() {
        this.searchClick.next(this.searchValue);
    }
}
