import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {DeptBreadcrumbNoChildComponent} from './dept-breadcrumb-no-child.component';

describe('DeptBreadcrumbNoChildComponent', () => {
    let component: DeptBreadcrumbNoChildComponent;
    let fixture: ComponentFixture<DeptBreadcrumbNoChildComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [DeptBreadcrumbNoChildComponent]
        })
            .compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(DeptBreadcrumbNoChildComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it('should create', () => {
        expect(component).toBeTruthy();
    });
});
