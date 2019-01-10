import {Component, EventEmitter, Input, OnChanges, OnInit, Output, ViewEncapsulation} from '@angular/core';
import {DialogsService} from '../../components/dialogs/dialogs.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {DailyTask} from '../../models/original/daily-task.model';
import {ObjectUtils} from '../../../shared/utils/ObjectUtils';
import {Location} from '@angular/common';

@Component({
    selector: 'app-form-daily-task',
    templateUrl: './form-daily-task.component.html',
    styleUrls: ['./form-daily-task.component.scss'],
    encapsulation: ViewEncapsulation.None
})
export class FormDailyTaskComponent implements OnInit, OnChanges {

    commonForm: FormGroup;

    @Input() dailyTask: DailyTask = new DailyTask();

    @Output() onTransmitFormValue: EventEmitter<{ obj: any }> = new EventEmitter(null);


    formErrors = {

        id: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        guid: [
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        taskName: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大32位长度',
            }
        ],
        growthValue: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大10位长度',
            }
        ],
        state: [
            {
                name: 'required',
                msg: '不可为空',
            },
            {
                name: 'maxlength',
                msg: '最大0位长度',
            }
        ],
    };

    constructor(private fb: FormBuilder, private location: Location, private dialogService: DialogsService) {
        this.buildForm();
    }

    ngOnChanges(value) {
        if (value.dailyTask !== undefined && value.dailyTask.currentValue !== undefined) {
            this.setBuildFormValue(this.dailyTask);
        }
    }

    ngOnInit() {

    }

    buildForm(): void {
        this.commonForm = this.fb.group({
            guid: [],
            taskName: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(32)
                ])
            ],
            growthValue: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(10)
                ])
            ],
            state: [
                null, Validators.compose([Validators.required,
                    Validators.maxLength(0)
                ])
            ],
        });
    }

    setBuildFormValue(dailyTask: DailyTask) {
        this.commonForm.setValue({
            guid:
            dailyTask.guid
            ,
            taskName:
            dailyTask.taskName
            ,
            growthValue:
            dailyTask.growthValue
            ,
            state:
            dailyTask.state
            ,
        });
    }

    submitCheck(): any {
        const commonFormValid = ObjectUtils.checkValidated(this.commonForm);
        if (commonFormValid) {
            return this.commonForm.value;
        }
        return null;
    }

    onSubmit() {
        const formValue = this.submitCheck();
        if (this.dailyTask) {
        }
        if (!formValue) {
            this.dialogService.toast('warning', '提示', '校验尚未通过无法提交，请确认输入项！');
            return;
        }
        console.log("commonForm value=" + JSON.stringify(formValue));
        this.onTransmitFormValue.emit({obj: formValue});
    }

    reset() {

    }

    goBack() {
        this.location.back();
    }

}
