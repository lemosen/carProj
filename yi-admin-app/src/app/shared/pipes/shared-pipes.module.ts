import {NgModule} from '@angular/core';
import {KeysPipe} from './keys.pipe';
import {HtmlToPlaintextPipe} from './htmlToPlaintext.pipe';
import {CamelCaseToDashPipe} from './camelCaseToDash.pipe';
import {AmTimeAgoPipe} from './amTimeAgo.pipe';
import {SecretPipe} from './secretString.pipe';
import {EllipsisStringPipe} from './ellipsis-string.pipe';
import {IsEmptyPipe} from './is-empty.pipe';
import {IsNotEmptyPipe} from './is-not-empty.pipe';
import {KeepHtmlPipe} from './keep-html.pipe';
import {BreakLinesPipe} from './break-lines.pipe';
import {FormMarkPipe} from './form-mark.pipe';
import {GetByIdPipe} from './getById.pipe';
import {FilterPipe} from './filter.pipe';
import {ComparePipe} from './compare.pipe';
import {AvatarPipe} from './avatar-pipe';
import {DataTypePipe} from './data-type.pipe';
import {FileExtPipe} from './file-ext.pipe';
import {ObjectTypePipe} from './object-type.pipe';
import {MailSearchPipe} from './search/mail-search.pipe';
import {TruncatePipe} from './truncate/truncate.pipe';
import {UserSearchPipe} from './search/user-search.pipe';
import {ChatPersonSearchPipe} from './search/chat-person-search.pipe';
import {ProfilePicturePipe} from './profilePicture/profilePicture.pipe';

@NgModule({
    declarations: [
        GetByIdPipe,
        FilterPipe,
        ComparePipe,
        KeysPipe,
        HtmlToPlaintextPipe,
        CamelCaseToDashPipe,
        AmTimeAgoPipe,
        SecretPipe,
        EllipsisStringPipe,
        IsEmptyPipe,
        IsNotEmptyPipe,
        KeepHtmlPipe,
        BreakLinesPipe,
        FormMarkPipe,
        AvatarPipe,
        DataTypePipe,
        FileExtPipe,
        ObjectTypePipe,
        ProfilePicturePipe,
        ChatPersonSearchPipe,
        UserSearchPipe,
        TruncatePipe,
        MailSearchPipe
    ],
    imports: [],
    exports: [
        GetByIdPipe,
        FilterPipe,
        ComparePipe,
        KeysPipe,
        HtmlToPlaintextPipe,
        CamelCaseToDashPipe,
        AmTimeAgoPipe,
        SecretPipe,
        EllipsisStringPipe,
        IsEmptyPipe,
        IsNotEmptyPipe,
        KeepHtmlPipe,
        BreakLinesPipe,
        FormMarkPipe,
        AvatarPipe,
        DataTypePipe,
        FileExtPipe,
        ObjectTypePipe,
        ProfilePicturePipe,
        ChatPersonSearchPipe,
        UserSearchPipe,
        TruncatePipe,
        MailSearchPipe
    ]
})

export class SharedPipesModule {

}

