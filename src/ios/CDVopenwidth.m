/********* CDVopenwidth.m Cordova Plugin Implementation *******/

#import <Cordova/CDV.h>

@interface CDVopenwidth : CDVPlugin {
  // Member variables go here.
    NSString *localFile;
}

- (void)open:(CDVInvokedUrlCommand*)command;
- (void) cleanupTempFile: (UIDocumentInteractionController *) controller;
@end

@implementation CDVopenwidth

UIDocumentInteractionController *openWithInteractionController;


- (void)open:(CDVInvokedUrlCommand*)command
{
    CDVPluginResult* pluginResult = nil;
    
    localFile = [command.arguments objectAtIndex:0];
    NSString *uti = [command.arguments objectAtIndex:1];
    NSString *fromTopStr = [command.arguments objectAtIndex:2];
    CGFloat fromTop = [fromTopStr floatValue];
    
    
    // Get file again from Documents directory
    NSURL *fileURL = [NSURL fileURLWithPath:localFile];
    
    
    if (openWithInteractionController == nil){
        openWithInteractionController = [UIDocumentInteractionController  interactionControllerWithURL:fileURL];
        openWithInteractionController.delegate = self;
        openWithInteractionController.UTI = uti;
    }else{
        openWithInteractionController.URL = fileURL;
        openWithInteractionController.UTI = uti;
    }
    
    
    UIDocumentInteractionController *controller = openWithInteractionController;
    
    CDVViewController* cont = (CDVViewController*)[ super viewController ];
    
    //UI_USER_INTERFACE_IDIOM
    if ( UI_USER_INTERFACE_IDIOM() == UIUserInterfaceIdiomPad )
    {
        /* Device is iPad */
        CGRect rect = CGRectMake(7, fromTop, 320, 38);
        [controller presentOptionsMenuFromRect:rect inView:cont.view animated:YES];
    }else{
        /* Device is iPhone */
        CGRect rect = CGRectMake(0, 0, cont.view.bounds.size.width, cont.view.bounds.size.height);
        [controller presentOpenInMenuFromRect:rect inView:cont.view animated:YES];
    }
    
    pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString: @""];
    
    [self.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
}


- (void) documentInteractionControllerDidDismissOpenInMenu:(UIDocumentInteractionController *)controller {
    //NSLog(@"documentInteractionControllerDidDismissOpenInMenu");
    
    [self cleanupTempFile:controller];
}

- (void) documentInteractionController: (UIDocumentInteractionController *) controller didEndSendingToApplication: (NSString *) application {
    //NSLog(@"didEndSendingToApplication: %@", application);
    
    [self cleanupTempFile:controller];
}

- (void) cleanupTempFile: (UIDocumentInteractionController *) controller
{
    
    NSFileManager *fileManager = [NSFileManager defaultManager];
    NSError *error;
    //BOOL fileExists = [fileManager fileExistsAtPath:localFile];
    
    //NSLog(@"Path to file: %@", localFile);   
    //NSLog(@"File exists: %d", fileExists);
    //NSLog(@"Is deletable file at path: %d", [fileManager isDeletableFileAtPath:localFile]);
    BOOL fileExists = NO;
    if (fileExists) 
    {
        BOOL success = [fileManager removeItemAtPath:localFile error:&error];
        if (!success) NSLog(@"Error: %@", [error localizedDescription]);
    }
    //[localFile release];
    //[controller release];
}

@end
