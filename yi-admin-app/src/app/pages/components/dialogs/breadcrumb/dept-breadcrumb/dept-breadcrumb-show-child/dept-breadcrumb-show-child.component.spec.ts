import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeptBreadcrumbShowChildComponent} from './dept-breadcrumb-show-child.component';

describe('DeptBreadcrumbShowChildComponent', () => {
    let component: DeptBreadcrumbShowChildComponent;
    let fixture: ComponentFixture<DeptBreadcrumbShowChildComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DeptBreadcrumbShowChildComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DeptBreadcrumbShowChildComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
